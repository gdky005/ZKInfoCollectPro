package cc.zkteam.zkinfocollectpro.localserver;


import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.EncryptUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;

import cc.zkteam.zkinfocollectpro.utils.L;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

import static okhttp3.internal.Util.closeQuietly;

/**
 * A wrapper on top of NanoHTTPD that provides a managed connection pool. Since Nano starts immediately, it immediately kills the started threads,
 * sets up the connection pool, and waits for connections on a background thread (like Nano). All other functionality inherited from NanoHTTPD.
 * <p>
 * 这是NanoHTTPD的包装器, 所有功能从 NanoHTTPD 继承。
 * 新增功能如下:
 * 1. 增加线程连接池管理。
 * 2. 增加本地文件缓存,并使用 Lru 功能控制文件的个数
 *
 * @author wangqing
 */

public class JMHTTPDPooled {

    public static final String TAG = "JMHTTPDPooled";

    /*
     * Where worker threads stand idle
     */
    private static ArrayList<JMHTTPSession> threads = new ArrayList<JMHTTPSession>();

    /**
     * max # worker threads
     */
    private static int workers = 8;
    /**
     * 最大缓存本地临时文件 100个
     */
    private int tempFileCacheCount = 100;


    protected int myTcpPort;
    protected File myRootDir;
    protected Context mContext;
    protected Thread myThread;
    protected JMLRUCache lruCache;
    protected ServerSocket myServerSocket;


    private static int theBufferSize = 16 * 1024;

    // Change this if you want to log to somewhere else than stdout
    protected static PrintStream myOut = System.out;
    /**
     * Hashtable mapping (String)FILENAME_EXTENSION -> (String)MIME_TYPE
     */
    private static Hashtable<String, String> theMimeTypes = new Hashtable<String, String>();

    /**
     * GMT date formatter
     */
    private static java.text.SimpleDateFormat gmtFrmt;

    static {
        gmtFrmt = new java.text.SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        gmtFrmt.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    static {
        StringTokenizer st = new StringTokenizer("css		text/css " + "htm		text/html " + "html		text/html " + "xml		text/xml " + "txt		text/plain " + "asc		text/plain " + "gif		image/gif " + "jpg		image/jpeg " + "jpeg		image/jpeg " + "png		image/png " + "mp3		audio/mpeg " + "m3u		audio/mpeg-url " + "mp4		video/mp4 " + "ogv		video/ogg " + "flv		video/x-flv " + "mov		video/quicktime " + "swf		application/x-shockwave-flash " + "js			application/javascript " + "pdf		application/pdf " + "doc		application/msword " + "ogg		application/x-ogg " + "zip		application/octet-stream " + "exe		application/octet-stream " + "class		application/octet-stream ");
        while (st.hasMoreTokens()) theMimeTypes.put(st.nextToken(), st.nextToken());
    }


    /**
     * 初始化 NanoHTTPD
     *
     * @param wwwroot
     */
    public JMHTTPDPooled(Context context, File wwwroot) {
        this.mContext = context;
        this.myRootDir = wwwroot;
        initCache();
    }

    public JMHTTPDPooled(Context context) {
        this.mContext = context;
    }

    /**
     * 初始化缓存
     */
    private void initCache() {
        lruCache = new JMLRUCache(tempFileCacheCount);
        lruCache.setOnLruRemoveListener(new JMLRUCache.OnLruRemoveListener() {
            @Override
            public void needRemoveEntry(Map.Entry eldest) {
                String path = (String) eldest.getValue();

                L.i("删除的数据是:  defaultValue:" + eldest.getValue());

                File file = new File(path);
                if (file.exists()) file.delete();
            }
        });
    }

    /**
     * Starts a HTTP server to given port.
     * <p>
     * Throws an IOException if the socket is already in use
     */
    public int getPort() {
        return this.myTcpPort;
    }

    public boolean start(int port) {
        this.myTcpPort = port;
        try {
            myServerSocket = new ServerSocket(myTcpPort);
        } catch (IOException e) {
            LogE(e, "start: ");
            return false;
        }
        if (port == 0) {
            this.myTcpPort = myServerSocket.getLocalPort();
        }
        for (int i = 0; i < workers; ++i) {
            JMHTTPSession w = new JMHTTPSession();
            (new Thread(w, "worker-#" + i)).start();
            threads.add(w);
        }
        myThread = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        JMHTTPSession w = null;
                        synchronized (threads) {
                            if (threads.isEmpty()) {
                                JMHTTPSession ws = new JMHTTPSession();
                                ws.setSocket(myServerSocket.accept());
                                (new Thread(ws, "additional-worker")).start();
                            } else {
                                w = threads.get(0);
                                threads.remove(0);
                                w.setSocket(myServerSocket.accept());
                            }
                        }
                    }
                } catch (IOException ioe) {
                    LogE(ioe);
                }
            }
        });
        myThread.setDaemon(true);
        myThread.start();
        return true;

    }

    /**
     * Override this to customize the server.
     * <p>
     * <p>
     * (By default, this delegates to serveFile() and allows directory listing.)
     *
     * @param uri    Percent-decoded URI without parameters, for example "/index.cgi"
     * @param method "GET", "POST" etc.
     * @param parms  Parsed, percent decoded parameters from URI and, in case of POST, data.
     * @param header Header entries, percent decoded
     * @return HTTP response, see class Response for details
     */
    public JMResponse serve(String uri, String method, Properties header, Properties parms, Properties files) {
        // 可以控制 log的输出,或者做其他处理。
        return serveFile(uri, header, myRootDir, true);
    }

    /**
     * HTTP response. Return one of these from serve().
     */
    public static class JMResponse {

        /**
         * Default constructor: response = HTTP_OK, data = mime = 'null'
         */
        public JMResponse() {
            this.status = HTTP_OK;
        }

        /**
         * Basic constructor.
         */
        public JMResponse(String status, String mimeType, InputStream data) {
            this.status = status;
            this.mimeType = mimeType;
            this.data = data;
        }

        /**
         * Convenience method that makes an InputStream out of given text.
         */
        public JMResponse(String status, String mimeType, String txt) {
            this.status = status;
            this.mimeType = mimeType;
            try {
                this.data = new ByteArrayInputStream(txt.getBytes("UTF-8"));
            } catch (java.io.UnsupportedEncodingException uee) {
                uee.printStackTrace();
            }
        }

        /**
         * Adds given line to the header.
         */
        public void addHeader(String name, String value) {
            header.put(name, value);
        }

        /**
         * HTTP gameStatus code after processing, e.g. "200 OK", HTTP_OK
         */
        public String status;

        /**
         * MIME type of content, e.g. "text/html"
         */
        public String mimeType;

        /**
         * Data of the response, may be null.
         */
        public InputStream data;

        /**
         * Headers for the HTTP response. Use addHeader() to add lines.
         */
        public Properties header = new Properties();
    }

    /**
     * Some HTTP response gameStatus codes
     */
    public static final String HTTP_OK = "200 OK", HTTP_PARTIALCONTENT = "206 Partial Content", HTTP_RANGE_NOT_SATISFIABLE = "416 Requested Range Not Satisfiable", HTTP_REDIRECT = "301 Moved Permanently", HTTP_NOTMODIFIED = "304 Not Modified", HTTP_FORBIDDEN = "403 Forbidden", HTTP_NOTFOUND = "404 Not Found", HTTP_BADREQUEST = "400 Bad Request", HTTP_INTERNALERROR = "500 Internal Server Error", HTTP_NOTIMPLEMENTED = "501 Not Implemented";

    /**
     * Common mime types for dynamic content
     */
    public static final String MIME_PLAINTEXT = "text/plain", MIME_HTML = "text/html", MIME_DEFAULT_BINARY = "application/octet-stream", MIME_XML = "text/xml";

    // ==================================================
    // Socket & server code
    // ==================================================

    /**
     * Stops the server.
     */
    public void stop() {
        try {
            myServerSocket.close();
            myThread.join();
            lruCache.clear();
            lruCache.setOnLruRemoveListener(null);
        } catch (IOException ioe) {
            LogE(ioe);
        } catch (InterruptedException e) {
            LogE(e);
        }
    }

    private void LogE(Exception e) {
        LogE(e, "run: e");
    }

    private void LogE(Exception e, String msg) {
        Log.e(TAG, msg, e);
    }


    /**
     * Handles one session, i.e. parses the HTTP request and returns the response.
     */
    protected class JMHTTPSession implements Runnable {
        public JMHTTPSession() {
        }

        public synchronized void setSocket(Socket s) {
            this.mySocket = s;
            notify();
        }

        public synchronized void run() {
            while (true) {
                if (mySocket == null) {
                    /* nothing to do */
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        /* should not happen */
                        continue;
                    }
                }
                try {
                    processRequest();
                } catch (Exception e) {
                    LogE(e, "run: e");
                }
                /*
                 * go back in wait queue if there's fewer than numHandler connections.
				 */
                mySocket = null;
                ArrayList<JMHTTPSession> pool = JMHTTPDPooled.threads;
                synchronized (pool) {
                    if (pool.size() >= JMHTTPDPooled.workers) {
                        /* too many threads, exit this one */
                        return;
                    } else {
                        pool.add(this);
                    }
                }
            }
        }

        /**
         *
         */
        protected void processRequest() {
            try {
                InputStream is = mySocket.getInputStream();
                if (is == null) return;

                // Read the first 8192 bytes.
                // The full header should fit in here.
                // Apache's default header limit is 8KB.
                int bufsize = 8192;
                byte[] buf = new byte[bufsize];
                int rlen = is.read(buf, 0, bufsize);
                if (rlen <= 0) return;


//                Source source = Okio.source(is);
//                BufferedSource bufferedSource = Okio.buffer(source);
//                String my = bufferedSource.readString(Charset.forName("UTF-8"));
//                L.i("当前的数据流是:" + my);
//
//                source.close();
//                bufferedSource.close();


                // Create a BufferedReader for parsing the header.
                ByteArrayInputStream hbis = new ByteArrayInputStream(buf, 0, rlen);
                BufferedReader hin = new BufferedReader(new InputStreamReader(hbis));
                Properties pre = new Properties();
                Properties parms = new Properties();
                Properties header = new Properties();
                Properties files = new Properties();

                // Decode the header into parms and header java properties
                decodeHeader(hin, pre, parms, header);
                String method = pre.getProperty("method");
                String uri = pre.getProperty("uri");

                long size = 0x7FFFFFFFFFFFFFFFl;
                String contentLength = header.getProperty("content-length");
                if (contentLength != null) {
                    try {
                        size = Integer.parseInt(contentLength);
                    } catch (NumberFormatException ex) {
                        LogE(ex);
                    }
                }

                // We are looking for the byte separating header from body.
                // It must be the last byte of the first two sequential new
                // lines.
                int splitbyte = 0;
                boolean sbfound = false;
                while (splitbyte < rlen) {
                    if (buf[splitbyte] == '\r' && buf[++splitbyte] == '\n' && buf[++splitbyte] == '\r' && buf[++splitbyte] == '\n') {
                        sbfound = true;
                        break;
                    }
                    splitbyte++;
                }
                splitbyte++;

                // Write the part of body already read to ByteArrayOutputStream
                // f
                ByteArrayOutputStream f = new ByteArrayOutputStream();
                if (splitbyte < rlen) f.write(buf, splitbyte, rlen - splitbyte);

                // While Firefox sends on the first read all the data fitting
                // our buffer, Chrome and Opera sends only the headers even if
                // there is data for the body. So we do some magic here to find
                // out whether we have already consumed part of body, if we
                // have reached the end of the data to be sent or we should
                // expect the first byte of the body at the next read.
                if (splitbyte < rlen) size -= rlen - splitbyte + 1;
                else if (!sbfound || size == 0x7FFFFFFFFFFFFFFFl) size = 0;

                // Now read all the body and write it to f
                buf = new byte[512];
                while (rlen >= 0 && size > 0) {
                    rlen = is.read(buf, 0, 512);
                    size -= rlen;
                    if (rlen > 0) f.write(buf, 0, rlen);
                }

                // Get the raw body as a byte []
                byte[] fbuf = f.toByteArray();

                // Create a BufferedReader for easily reading it as string.
                ByteArrayInputStream bin = new ByteArrayInputStream(fbuf);
                BufferedReader in = new BufferedReader(new InputStreamReader(bin));

                // If the method is POST, there may be parameters
                // in data section, too, read it:
                if (method.equalsIgnoreCase("POST")) {
                    String contentType = "";
                    String contentTypeHeader = header.getProperty("content-type");
                    StringTokenizer st = new StringTokenizer(contentTypeHeader, "; ");
                    if (st.hasMoreTokens()) {
                        contentType = st.nextToken();
                    }

                    if (contentType.equalsIgnoreCase("multipart/form-data")) {
                        // Handle multipart/form-data
                        if (!st.hasMoreTokens())
                            sendError(HTTP_BADREQUEST, "BAD REQUEST: Content type is multipart/form-data but boundary missing. Usage: GET /example/file.html");
                        String boundaryExp = st.nextToken();
                        st = new StringTokenizer(boundaryExp, "=");
                        if (st.countTokens() != 2)
                            sendError(HTTP_BADREQUEST, "BAD REQUEST: Content type is multipart/form-data but boundary syntax error. Usage: GET /example/file.html");
                        st.nextToken();
                        String boundary = st.nextToken();

                        decodeMultipartData(boundary, fbuf, in, parms, files);
                    } else {
                        // Handle application/x-www-form-urlencoded
                        String postLine = "";
                        char pbuf[] = new char[512];
                        int read = in.read(pbuf);
                        while (read >= 0 && !postLine.endsWith("\r\n")) {
                            postLine += String.valueOf(pbuf, 0, read);
                            read = in.read(pbuf);
                        }
                        postLine = postLine.trim();
                        decodeParms(postLine, parms);
                    }
                }

                if (method.equalsIgnoreCase("PUT"))
                    files.put("content", saveTmpFile(fbuf, 0, f.size()));

                // Ok, now do the serve()
                JMResponse r = serve(uri, method, header, parms, files);
                if (r == null)
                    sendError(HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: Serve() returned a null response.");
                else
                    sendResponse(r.status, r.mimeType, r.header, r.data);

                in.close();
                is.close();
            } catch (IOException ioe) {
                try {
                    sendError(HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
                } catch (Exception t) {
                    LogE(ioe);
                }
                LogE(ioe);
            } catch (InterruptedException ie) {
                // Thrown by sendError, ignore and exit the thread.
                LogE(ie, "run: e");
            }
        }

        /**
         * Decodes the sent headers and loads the data into java Properties' key - defaultValue pairs
         **/
        private void decodeHeader(BufferedReader in, Properties pre, Properties parms, Properties header) throws InterruptedException {
            try {
                // Read the request line
                String inLine = in.readLine();
                if (inLine == null) return;
                StringTokenizer st = new StringTokenizer(inLine);
                if (!st.hasMoreTokens())
                    sendError(HTTP_BADREQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html");

                String method = st.nextToken();
                pre.put("method", method);

                if (!st.hasMoreTokens())
                    sendError(HTTP_BADREQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");

                String uri = st.nextToken();

                // Decode parameters from the URI
                int qmi = uri.indexOf('?');
                if (qmi >= 0) {
                    decodeParms(uri.substring(qmi + 1), parms);
                    uri = decodePercent(uri.substring(0, qmi));
                } else uri = decodePercent(uri);

                // If there's another token, it's protocol version,
                // followed by HTTP headers. Ignore version but parse headers.
                // NOTE: this now forces header names lowercase since they are
                // case insensitive and vary by client.
                if (st.hasMoreTokens()) {
                    String line = in.readLine();
                    while (line != null && line.trim().length() > 0) {
                        int p = line.indexOf(':');
                        if (p >= 0)
                            header.put(line.substring(0, p).trim().toLowerCase(), line.substring(p + 1).trim());
                        line = in.readLine();
                    }
                }
                //还原 url。


//                // 2017/3/7 V2 本版本起 直接从 referer 中获取 url,而不是从后面截取，提高性能。
//                String realUrl = header.getProperty(ConstantUtil.TEXT_FILED_REFERER);
//
//                // T2017/3/7 以下专为 电脑测试网页功能使用。
//                String userAgent = header.getProperty(ConstantUtil.TEXT_FILED_USER_AGENT);
//
//                if (!TextUtils.isEmpty(uri)) {
//                    if (userAgent.contains(ConstantUtil.TEXT_FILED_USER_MAC) && uri.contains(ConstantUtil.TEST_LOCAL_SERVER_INDEX_HTML)) {
//                        uri = ConstantUtil.TEST_LOCAL_SERVER_VERSION_DIR + uri;
//                    } else if (userAgent.contains(ConstantUtil.TEXT_FILED_USER_MAC) && !uri.contains(ConstantUtil.TEST_LOCAL_SERVER_INDEX_HTML)) {
//                        realUrl = null;
//                        uri = ConstantUtil.TEST_LOCAL_SERVER_VERSION_DIR + uri;
//                    } else if (TextUtils.isEmpty(realUrl) && realUrl.equals(ConstantUtil.TEXT_FILED_USER_PORT)) {
//                        // 2017/3/7  暂时默认这里取数据
//                        uri = ConstantUtil.TEST_LOCAL_SERVER_VERSION_DIR + uri;
//                    }
//                }
//
////                //  2017/3/7 V2 本版本起 直接从 referer 中获取 url,而不是从后面截取，提高性能。
////                String realUrl = header.getProperty("referer");
//
//                if (TextUtils.isEmpty(realUrl))
//                    realUrl = uri;
////                String host = parms.getProperty(SEPARATOR_TAG);
////
////                if (!TextUtils.isEmpty(host)) {
////                    String decodeInLine = decodePercent(inLine);
////                    realUrl = host + decodeInLine.substring(decodeInLine.indexOf(uri), decodeInLine.lastIndexOf(SEPARATOR_TAG) - 1);
////                } else {
////                    realUrl = uri;
////                }

                L.i("当前真实的 URL:" + uri + ", 默认的 url是:" + uri);
                pre.put("uri", uri);
            } catch (IOException ioe) {
                LogE(ioe);
                sendError(HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            }
        }

        /**
         * Decodes the Multipart Body data and put it into java Properties' key - defaultValue pairs.
         **/
        private void decodeMultipartData(String boundary, byte[] fbuf, BufferedReader in, Properties parms, Properties files) throws InterruptedException {
            try {
                int[] bpositions = getBoundaryPositions(fbuf, boundary.getBytes());
                int boundarycount = 1;
                String mpline = in.readLine();
                while (mpline != null) {
                    if (mpline.indexOf(boundary) == -1)
                        sendError(HTTP_BADREQUEST, "BAD REQUEST: Content type is multipart/form-data but next chunk does not start with boundary. Usage: GET /example/file.html");
                    boundarycount++;
                    Properties item = new Properties();
                    mpline = in.readLine();
                    while (mpline != null && mpline.trim().length() > 0) {
                        int p = mpline.indexOf(':');
                        if (p != -1)
                            item.put(mpline.substring(0, p).trim().toLowerCase(), mpline.substring(p + 1).trim());
                        mpline = in.readLine();
                    }
                    if (mpline != null) {
                        String contentDisposition = item.getProperty("content-disposition");
                        if (contentDisposition == null) {
                            sendError(HTTP_BADREQUEST, "BAD REQUEST: Content type is multipart/form-data but no content-disposition info found. Usage: GET /example/file.html");
                        }
                        StringTokenizer st = new StringTokenizer(contentDisposition, "; ");
                        Properties disposition = new Properties();
                        while (st.hasMoreTokens()) {
                            String token = st.nextToken();
                            int p = token.indexOf('=');
                            if (p != -1)
                                disposition.put(token.substring(0, p).trim().toLowerCase(), token.substring(p + 1).trim());
                        }
                        String pname = disposition.getProperty("name");
                        pname = pname.substring(1, pname.length() - 1);

                        String value = "";
                        if (item.getProperty("content-type") == null) {
                            while (mpline != null && mpline.indexOf(boundary) == -1) {
                                mpline = in.readLine();
                                if (mpline != null) {
                                    int d = mpline.indexOf(boundary);
                                    if (d == -1) value += mpline;
                                    else value += mpline.substring(0, d - 2);
                                }
                            }
                        } else {
                            if (boundarycount > bpositions.length)
                                sendError(HTTP_INTERNALERROR, "Error processing request");
                            int offset = stripMultipartHeaders(fbuf, bpositions[boundarycount - 2]);
                            String path = saveTmpFile(fbuf, offset, bpositions[boundarycount - 1] - offset - 4);
                            files.put(pname, path);
                            value = disposition.getProperty("filename");
                            value = value.substring(1, value.length() - 1);
                            do {
                                mpline = in.readLine();
                            }
                            while (mpline != null && mpline.indexOf(boundary) == -1);
                        }
                        parms.put(pname, value);
                    }
                }
            } catch (IOException ioe) {
                LogE(ioe);
                sendError(HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            }
        }

        /**
         * Find the byte positions where multipart boundaries start.
         **/
        public int[] getBoundaryPositions(byte[] b, byte[] boundary) {
            int matchcount = 0;
            int matchbyte = -1;
            Vector<Integer> matchbytes = new Vector<Integer>();
            for (int i = 0; i < b.length; i++) {
                if (b[i] == boundary[matchcount]) {
                    if (matchcount == 0) matchbyte = i;
                    matchcount++;
                    if (matchcount == boundary.length) {
                        matchbytes.addElement(new Integer(matchbyte));
                        matchcount = 0;
                        matchbyte = -1;
                    }
                } else {
                    i -= matchcount;
                    matchcount = 0;
                    matchbyte = -1;
                }
            }
            int[] ret = new int[matchbytes.size()];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = ((Integer) matchbytes.elementAt(i)).intValue();
            }
            return ret;
        }

        /**
         * Retrieves the content of a sent file and saves it to a temporary file. The full path to the saved file is returned.
         **/
        private String saveTmpFile(byte[] b, int offset, int len) {
            String path = "";
            if (len > 0) {
                String tmpdir = System.getProperty("java.io.tmpdir");
                try {
                    File temp = File.createTempFile("KCHTTPD", "", new File(tmpdir));
                    OutputStream fstream = new FileOutputStream(temp);
                    fstream.write(b, offset, len);
                    fstream.close();
                    path = temp.getAbsolutePath();
                } catch (Exception e) { // Catch exception if any
                    LogE(e, "run: e");
                }
            }
            return path;
        }

        /**
         * It returns the offset separating multipart file headers from the file's data.
         **/
        private int stripMultipartHeaders(byte[] b, int offset) {
            int i = 0;
            for (i = offset; i < b.length; i++) {
                if (b[i] == '\r' && b[++i] == '\n' && b[++i] == '\r' && b[++i] == '\n') break;
            }
            return i + 1;
        }

        /**
         * Decodes the percent encoding scheme. <br/>
         * For example: "an+example%20string" -> "an example string"
         */
        private String decodePercent(String str) throws InterruptedException {
            try {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
                    switch (c) {
                        case '+':
                            sb.append(' ');
                            break;
                        case '%':
                            sb.append((char) Integer.parseInt(str.substring(i + 1, i + 3), 16));
                            i += 2;
                            break;
                        default:
                            sb.append(c);
                            break;
                    }
                }
                return sb.toString();
            } catch (Exception e) {
                LogE(e, "run: e");
                sendError(HTTP_BADREQUEST, "BAD REQUEST: Bad percent-encoding.");
                return null;
            }
        }

        /**
         * Decodes parameters in percent-encoded URI-format ( e.g. "name=Jack%20Daniels&pass=Single%20Malt" ) and adds them to given Properties. NOTE:
         * this doesn't support multiple identical keys due to the simplicity of Properties -- if you need multiples, you might want to replace the
         * Properties with a Hashtable of Vectors or such.
         */
        public void decodeParms(String parms, Properties p) throws InterruptedException {
            if (parms == null) return;

            StringTokenizer st = new StringTokenizer(parms, "&");
            while (st.hasMoreTokens()) {
                String e = st.nextToken();
                int sep = e.indexOf('=');
                if (sep >= 0)
                    p.put(decodePercent(e.substring(0, sep)).trim(), decodePercent(e.substring(sep + 1)));
            }
        }

        /**
         * Returns an error message as a HTTP response and throws InterruptedException to stop further request processing.
         */
        private void sendError(String status, String msg) throws InterruptedException {
            sendResponse(status, MIME_PLAINTEXT, null, new ByteArrayInputStream(msg.getBytes()));
            throw new InterruptedException();
        }

        /**
         * Sends given response to the socket.
         */
        private void sendResponse(String status, String mime, Properties header, InputStream data) {
            try {
                if (status == null) throw new Error("sendResponse(): Status can't be null.");

                OutputStream out = mySocket.getOutputStream();
                PrintWriter pw = new PrintWriter(out);
                pw.print("HTTP/1.0 " + status + " \r\n");

                if (mime != null) pw.print("Content-Type: " + mime + "\r\n");

                if (header == null || header.getProperty("Date") == null)
                    pw.print("Date: " + gmtFrmt.format(new Date()) + "\r\n");

                if (header != null) {
                    Enumeration<?> e = header.keys();
                    while (e.hasMoreElements()) {
                        String key = (String) e.nextElement();
                        String value = header.getProperty(key);
                        pw.print(key + ": " + value + "\r\n");
                    }
                }

                pw.print("\r\n");
                pw.flush();

                if (data != null) {
                    int pending = data.available(); // This is to support
                    // partial sends, see
                    // serveFile()
                    byte[] buff = new byte[theBufferSize];
                    while (pending > 0) {
                        int read = data.read(buff, 0, ((pending > theBufferSize) ? theBufferSize : pending));
                        if (read <= 0) break;
                        out.write(buff, 0, read);
                        pending -= read;
                    }
                }
                out.flush();
                out.close();
                if (data != null) data.close();
            } catch (IOException ioe) {
                // Couldn't write? No can do.
                LogE(ioe);
                try {
                    mySocket.close();
                } catch (Exception t) {
                    LogE(t, "run: e");
                }
            }
        }

        private Socket mySocket;
    }

    /**
     * URL-encodes everything between "/"-characters. Encodes spaces as '%20' instead of '+'.
     */
    private String encodeUri(String uri) {
        String newUri = "";
        StringTokenizer st = new StringTokenizer(uri, "/ ", true);
        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (tok.equals("/")) newUri += "/";
            else if (tok.equals(" ")) newUri += "%20";
            else {
                newUri += URLEncoder.encode(tok);
                // For Java 1.4 you'll want to use this instead:
                // try { newUri += URLEncoder.encode( tok, "UTF-8" ); } catch (
                // java.io.UnsupportedEncodingException uee ) {}
            }
        }
        return newUri;
    }

    // ==================================================
    // File server code
    // ==================================================

    /**
     * Serves file from homeDir and its' subdirectories (only). Uses only URI, ignores all headers and HTTP parameters.
     */
    public JMResponse serveFile(String uri, Properties header, File homeDir, boolean allowDirectoryListing) {

        String md5Url = null;
        JMResponse res = null;

        L.i("当前的 Uri是:" + uri);

//        已经将该代码移动到外层处理：JMWebView.getFrescoWebResourceResponse() 处理。暂时保留
//        InputStream fileInputStream = null;
//        if (uri.contains(".png") || uri.contains(".jpg")) {
//            // 2017/3/9  直接从 Fresco 中获取图片
//            fileInputStream = JMPicManager.getInstance().getPicInputStream(mContext, uri);
//
//            if (fileInputStream != null) {
//                String mime = getMime(uri);
//                res = new JMResponse(HTTP_OK, mime, fileInputStream);
//                res.addHeader("Accept-Ranges", "bytes");
//            }
//
//            //  2017/3/13 Test
//            setCookies(res, header);
//
//            return res;
//        }





        //  2017/3/7 V2 先暂时去掉。
//        //首先去根目录下面 将 url MD5 后, 查找文件,查找成功就返回数据,查找失败就发起网络请求并存储文件。
//        if (!TextUtils.isEmpty(uri) && uri.lastIndexOf("/") != 0) { //没有使用 md5处理的图片名字
//            //1. 对Url 的名字做 MD5处理。
//            md5Url = MD5Util.md5(uri);
//            L.i("url:" + uri + ", md5Url:" + md5Url);
//
//            res = getLocalCacheRes(uri, homeDir, md5Url, res);
//
//            if (res != null)
//                return res;
//        }

        // Make sure we won't die of an exception later
        if (!homeDir.isDirectory())
            res = new JMResponse(HTTP_INTERNALERROR, MIME_PLAINTEXT, "INTERNAL ERRROR: serveFile(): given homeDir is not a directory.");

        if (res == null) {
            // Remove URL arguments
            uri = uri.trim().replace(File.separatorChar, '/');
            if (uri.indexOf('?') >= 0) uri = uri.substring(0, uri.indexOf('?'));

            // Prohibit getting out of current directory
            if (uri.startsWith("..") || uri.endsWith("..") || uri.indexOf("../") >= 0)
                res = new JMResponse(HTTP_FORBIDDEN, MIME_PLAINTEXT, "FORBIDDEN: Won't serve ../ for security reasons.");
        }

        File f = new File(homeDir, uri);

        L.i("要读取的文件地址是： " + f.getAbsolutePath());

        //  2017/3/7 V2 去除 MD5 校验功能，在本版本中无需处理，以后保留
//        //修改为md5的处理方式
//        if (res == null && !f.exists()) {
//            res = getJmResponse(uri, md5Url, res, f);
//
//            if (res == null)
//                res = new JMResponse(HTTP_NOTFOUND, MIME_PLAINTEXT, "Error 404, file not found.");
//        }

        // List the directory, if necessary
        if (res == null && f.isDirectory()) {
            // Browsers get confused without '/' after the
            // directory, send a redirect.
            if (!uri.endsWith("/")) {
                uri += "/";
                res = new JMResponse(HTTP_REDIRECT, MIME_HTML, "<html><body>Redirected: <a href=\"" + uri + "\">" + uri + "</a></body></html>");
                res.addHeader("Location", uri);
            }

            if (res == null) {
                // First try index.html and index.htm
                if (new File(f, "index.html").exists()) f = new File(homeDir, uri + "/index.html");
                else if (new File(f, "index.htm").exists())
                    f = new File(homeDir, uri + "/index.htm");
                    // No index file, list the directory if it is readable
                else if (allowDirectoryListing && f.canRead()) {
                    String[] files = f.list();
                    String msg = "<html><body><h1>Directory " + uri + "</h1><br/>";

                    if (uri.length() > 1) {
                        String u = uri.substring(0, uri.length() - 1);
                        int slash = u.lastIndexOf('/');
                        if (slash >= 0 && slash < u.length())
                            msg += "<b><a href=\"" + uri.substring(0, slash + 1) + "\">..</a></b><br/>";
                    }

                    if (files != null) {
                        for (int i = 0; i < files.length; ++i) {
                            File curFile = new File(f, files[i]);
                            boolean dir = curFile.isDirectory();
                            if (dir) {
                                msg += "<b>";
                                files[i] += "/";
                            }

                            msg += "<a href=\"" + encodeUri(uri + files[i]) + "\">" + files[i] + "</a>";

                            // Show file size
                            if (curFile.isFile()) {
                                long len = curFile.length();
                                msg += " &nbsp;<font size=2>(";
                                if (len < 1024) msg += len + " bytes";
                                else if (len < 1024 * 1024)
                                    msg += len / 1024 + "." + (len % 1024 / 10 % 100) + " KB";
                                else
                                    msg += len / (1024 * 1024) + "." + len % (1024 * 1024) / 10 % 100 + " MB";

                                msg += ")</font>";
                            }
                            msg += "<br/>";
                            if (dir) msg += "</b>";
                        }
                    }
                    msg += "</body></html>";
                    res = new JMResponse(HTTP_OK, MIME_HTML, msg);
                } else {
                    res = new JMResponse(HTTP_FORBIDDEN, MIME_PLAINTEXT, "FORBIDDEN: No directory listing.");
                }
            }
        }

        try {
            if (res == null) {
                String mime = getMime(f);

                // Calculate etag
                String etag = getETag(f);

                // Support (simple) skipping:
                long startFrom = 0;
                long endAt = -1;
                String range = header.getProperty("range");
                if (range != null) {
                    if (range.startsWith("bytes=")) {
                        range = range.substring("bytes=".length());
                        int minus = range.indexOf('-');
                        try {
                            if (minus > 0) {
                                startFrom = Long.parseLong(range.substring(0, minus));
                                endAt = Long.parseLong(range.substring(minus + 1));
                            }
                        } catch (NumberFormatException nfe) {
                            LogE(nfe, "run: e");
                        }
                    }
                }

                // Change return code and add Content-Range header when skipping
                // is requested
                long fileLen = f.length();
                if (range != null && startFrom >= 0) {
                    if (startFrom >= fileLen) {
                        res = new JMResponse(HTTP_RANGE_NOT_SATISFIABLE, MIME_PLAINTEXT, "");
                        res.addHeader("Content-Range", "bytes 0-0/" + fileLen);
                        res.addHeader("ETag", etag);
                    } else {
                        if (endAt < 0) endAt = fileLen - 1;
                        long newLen = endAt - startFrom + 1;
                        if (newLen < 0) newLen = 0;

                        final long dataLen = newLen;
                        FileInputStream fis = new FileInputStream(f) {
                            public int available() throws IOException {
                                return (int) dataLen;
                            }
                        };
                        fis.skip(startFrom);

                        res = new JMResponse(HTTP_PARTIALCONTENT, mime, fis);
                        res.addHeader("Content-Length", "" + dataLen);
                        res.addHeader("Content-Range", "bytes " + startFrom + "-" + endAt + "/" + fileLen);
                        res.addHeader("ETag", etag);
                    }
                } else {
                    if (etag.equals(header.getProperty("if-none-match")))
                        res = new JMResponse(HTTP_NOTMODIFIED, mime, "");
                    else {
                        // 这是正常的数据流,下面测试本地有图片,但是 真实图片从服务器获取
                        if (f.exists()) {
                            res = new JMResponse(HTTP_OK, mime, new FileInputStream(f));
                            res.addHeader("Content-Length", "" + fileLen);
                            res.addHeader("ETag", etag);

                            L.i("文件来源: 从本地 SD 卡获取图片:" + f.getPath() + ", 原图:" + uri);
                        } else {
                            res = new JMResponse(HTTP_NOTFOUND, mime, "404 文件不存在");
                        }
                    }
                }
            }
        } catch (IOException ioe) {
            LogE(ioe);
            res = new JMResponse(HTTP_FORBIDDEN, MIME_PLAINTEXT, "FORBIDDEN: Reading file failed.");
        }

        res.addHeader("Accept-Ranges", "bytes"); // Announce that the file

        setCookies(res, header);


        // server accepts partial
        // content requestes
        return res;
    }

    private void setCookies(JMResponse res, Properties header) {

        if (res == null)
            return;

        String cookie = header.getProperty("cookie");
        String url = header.getProperty("referer");


        //  2017/3/13  这里暂时为 ip, 后面修改为 localhost
        String localHostUrl = JMHttpServer.getInstance().getLocalHostUrl(mContext);

        Uri uri = Uri.parse(localHostUrl);
        String localUrl = uri.getHost();

        if (localUrl.contains("www")) {
            localUrl = localUrl.replaceAll("www", "");
        }

    }


    public static String transMapToString(Map map) {
        StringBuilder sb = new StringBuilder();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            sb.append(key).append("=").append(map.get(key));

            if (iterator.hasNext())
                sb.append("; ");
        }
        return sb.toString();
    }



    /**
     * 获取本地 缓存的 JMResponse
     *
     * @param uri
     * @param homeDir
     * @param md5Url
     * @param res
     * @return
     */
    private JMResponse getLocalCacheRes(String uri, File homeDir, String md5Url, JMResponse res) {
        // 从本地 获取数据流
        InputStream inputStream = (InputStream) getCacheDataFromSDcard(homeDir, md5Url, true);
        // 从本地 获取对应的 header 信息
        Headers headers = getHeaders(homeDir, md5Url);
        // 根据 本地的 数据流 和 header 返回对应的 JMResponse
        res = getJMResponseData(uri, headers, inputStream);

        return res;
    }

    /**
     * 根据必备信息返回  JMResponse
     *
     * @param uri
     * @param headers
     * @param inputStream
     * @return
     */
    @NonNull
    private JMResponse getJMResponseData(String uri, Headers headers, InputStream inputStream) {
        JMResponse res = null;//4. 查到该文件, 返回数据。 文件类型,需要根据请求的 url 的后缀去判断获取

        L.i("文件来源: 从本地 SD 卡获取图片: 原图:" + uri);

        String mime;
        if (headers != null) {
            mime = headers.get("content-type");
        } else {
            mime = getMime(uri);
        }

        if (inputStream != null)
            res = new JMResponse(HTTP_OK, mime, inputStream);

        return res;
    }

    /**
     * 查询本地存储的 Header 信息
     *
     * @param homeDir
     * @param md5Url
     * @return
     */
    private Headers getHeaders(File homeDir, String md5Url) {
        //查询 header 信息
        File headersDir = new File(homeDir, HEADER_DIR);

        String content = (String) getCacheDataFromSDcard(headersDir, md5Url, false);

        if (!TextUtils.isEmpty(content)) {
            String[] headersStr = content.split("\n");
            Headers.Builder builder = new Headers.Builder();

            for (String str : headersStr) {
                builder.add(str);
            }

            Headers headers = builder.build();
            if (headers != null)
                return headers;
        }
        return null;
    }

    /**
     * @param dir
     * @param md5Url
     * @param isNeedStreamType 是否需要 数据流(InputStream), 否则就 返回 String
     * @return 根据 isNeedStreamType 返回对应数据类型,默认支持 InputStream 和 String, 需要自行强转
     */
    @Nullable
    private Object getCacheDataFromSDcard(File dir, String md5Url, boolean isNeedStreamType) {
        if (dir == null) return null;

//        遍历根目录下面的文件 (用缓存存储目录里面的文件信息)
        File[] files = dir.listFiles();

        if (files != null && files.length > 0) {
            //遍历该目录是否存在该文件的 MD5值。
            for (File file : files) {
                String headerFileName = file.getName();
                if (md5Url != null && md5Url.equals(headerFileName)) {
                    Source source = null;
                    BufferedSource bufferedSource = null;

                    try {
                        source = Okio.source(file);
                        bufferedSource = Okio.buffer(source);

                        if (isNeedStreamType) { //文件数据存储信息
                            if (lruCache.get(md5Url) == null)
                                lruCache.put(md5Url, file.getPath());

                            return getByteArrayInputStream(bufferedSource.inputStream());
                        } else { //这是 Header 信息
                            String content = bufferedSource.readUtf8();
                            L.i("读取的数据是:" + content);
                            return content;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        closeQuietly(source);
                        closeQuietly(bufferedSource);
                    }
                    break;
                }
            }
        }
        return null;
    }

    private String getETag(File currentFile) {
        // Calculate etag
        return Integer.toHexString((currentFile.getAbsolutePath() + currentFile.lastModified() + "" + currentFile.length()).hashCode());
    }

    /**
     * 本地没有缓存文件,说明要从服务器同步下载获取文件
     *
     * @param uri    当前请求的 URL
     * @param md5Url urlMd5后的值
     * @param res    返回的数据实体
     * @param f      获取etag
     * @return
     */
    private JMResponse getJmResponse(String uri, String md5Url, JMResponse res, File f) {
        //本地没有缓存文件,说明要从服务器同步下载获取文件
        try {
            Response response = getResponse(uri);


            ResponseBody responseBody = response.body();
            MediaType mediaType = responseBody.contentType();
            InputStream inputStream = responseBody.byteStream();
            ByteArrayOutputStream outputStream = getByteArrayOutputStream(inputStream);
            response.close();

//            //对于没有后缀的网页,不能存储为文件
//            URI myUri = URI.create(uri);
//            String myUriPath = myUri.getPath();
//            if (myUriPath.contains(".")) {
//                saveFile2SDCard(md5Url, outputStream);
//            } else {
//                L.i("当前没有包含点的 URL:" + myUri);
//            }


            saveFile2SDCard(md5Url, response.headers(), outputStream);


            String mediaTypeStr = null;
            if (mediaType != null) {
                mediaTypeStr = mediaType.toString();
            }

            res = new JMResponse(String.valueOf(response.code()), mediaTypeStr,
                    new ByteArrayInputStream(outputStream.toByteArray()));

            String etag = getETag(f);
            res.addHeader("Content-Length", "" + responseBody.contentLength());
            // Calculate etag
            res.addHeader("ETag", etag);

            L.i("文件来源: 从服务器获取图片:" + uri);

            //将服务器 返回的 header 信息原封不动的返回到手机端
//                            Headers headers = response.headers();
//                            Set<String> stringSet = headers.names();
//                            for (String key :
//                                    stringSet) {
//                                res.addHeader(key, headers.get(key));
//                            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private Response getResponse(String uri) throws IOException {
        //使用 OKHttp 发起网络请求
        OkHttpClient client = new OkHttpClient();
//        Request.Builder request = new Request.Builder().url(uri);
        String UA = "Mozilla/5.0 (Linux; Android 7.0; Nexus 6P Build/NRD90M; wv) AppleWebKit/537" +
                ".36 (KHTML, like Gecko) Version/4.0 Chrome/52.0.2743.98 Mobile Safari/537.36";
        String ua = System.getProperty("http.agent");
        Request request = new Request.Builder().url(uri).addHeader("User-Agent", UA).build();
        return client.newCall(request).execute();
    }

    /**
     * 给 WebView 离线一组白名单 的数据文件到本地
     *
     * @param whiteLists
     */
    public void offlineListData(List<String> whiteLists) {
        for (String whiteItem : whiteLists)
            offlineData(whiteItem);
    }

    /**
     * 给 WebView 离线一个 url 的数据文件到本地
     *
     * @param url
     */
    public void offlineData(String url) {
        try {
            String md5Url =  EncryptUtils.encryptMD5ToString(url);
            Response response = getResponse(url);

            L.i("离线文件: " + url + ", md5后的是:" + md5Url);

            ResponseBody responseBody = response.body();
            InputStream inputStream = responseBody.byteStream();
            ByteArrayOutputStream outputStream = getByteArrayOutputStream(inputStream);
            response.close();

            saveFile2SDCard(md5Url, response.headers(), outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    public static final String DEFAULT_ROOT_DIR = SDCardUtil.getSDCardPath(context) + "/-0";
    public static final String HEADER_DIR = "headersDir";

    /**
     * 将请求到数据流保存到本地文件, 文件名用 MD5 后的值,便于检索
     *
     * @param md5Url
     * @param headers
     * @param outputStream
     */
    private void saveFile2SDCard(String md5Url, Headers headers, ByteArrayOutputStream outputStream) {
        if (myRootDir == null || !myRootDir.isDirectory()) {

            PathManager pathManager = PathManager.getInstance(mContext);

            myRootDir = new File(pathManager.getHomeDir());

            boolean state = myRootDir.mkdirs();
            L.i("创建 myRootDir的状态：" + state + ", 路径是：" + myRootDir.getAbsolutePath());
        }

        File file = new File(myRootDir, md5Url);
        if (file.exists()) {
            L.i("离线文件 已经存在");
            return;
        }

        //Save data 2 file
        try {
            Sink sink = Okio.sink(file);
            BufferedSink bufferedSink = Okio.buffer(sink);
            bufferedSink.writeAll(Okio.source(new ByteArrayInputStream(outputStream.toByteArray())));
            closeQuietly(bufferedSink);

            L.i("离线文件成功: " + md5Url);

            if (lruCache != null)
                lruCache.put(md5Url, file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Save header 2 file
        try {
            File headersDir = new File(myRootDir, HEADER_DIR);
            if (!headersDir.exists()) {
                headersDir.mkdirs();
            }

            File headerFile = new File(headersDir, md5Url);
            if (headerFile == null || headerFile.exists()) {
                L.i("离线的 Header 文件 已经存在");
                return;
            }

            Sink sink = Okio.sink(headerFile);
            BufferedSink bufferedSink = Okio.buffer(sink);
            bufferedSink.writeUtf8(headers.toString());
            closeQuietly(bufferedSink);

            L.i("离线文件的 Header成功: " + md5Url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private String getMime(File f) throws IOException {
        String path = f.getCanonicalPath();
        return getMime(path);
    }

    /**
     * 获取 mime
     *
     * @param mimeStr
     * @return
     */
    @NonNull
    public static String getMime(String mimeStr) {
        // Get MIME type from file name extension, if possible
        String mime = null;
        int dot = mimeStr.lastIndexOf('.');
        if (dot >= 0)
            mime = (String) theMimeTypes.get(mimeStr.substring(dot + 1).toLowerCase());
        if (mime == null) mime = MIME_DEFAULT_BINARY;
        return mime;
    }

    /**
     * 将数据流 InputStream 临时保存到 ByteArrayOutputStream, 可以让 InputStream 多次使用。
     *
     * @param input
     * @return
     */
    public ByteArrayOutputStream getByteArrayOutputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            return baos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将 数据流 InputStream 包装成 ByteArrayInputStream, 可以让 InputStream 多次使用
     *
     * @param input InputStream
     * @return ByteArrayInputStream
     */
    public ByteArrayInputStream getByteArrayInputStream(InputStream input) {
        try {
            int bytesRead = 0;
            byte[] buff = new byte[1024];
            ByteArrayOutputStream bao = new ByteArrayOutputStream();

            while ((bytesRead = input.read(buff)) != -1) {
                bao.write(buff, 0, bytesRead);
            }

            byte[] data = bao.toByteArray();

            return new ByteArrayInputStream(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getServerName(Context context) {
        //  2017/3/7 V2 这里直接写上 127.0.0.1， 现在用 ip，可以在本地测试。
        return "http://" + getLocalIpStr(context) + ":" + this.myTcpPort;
    }

    public static String getLocalIpStr(Context context) {
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            return intToIpAddr(wifiInfo.getIpAddress());
        }
        return null;
    }

    private static String intToIpAddr(int ip) {
        return (ip & 0xff) + "." + ((ip >> 8) & 0xff) + "." + ((ip >> 16) & 0xff) + "." + ((ip >> 24) & 0xff);
    }

}
