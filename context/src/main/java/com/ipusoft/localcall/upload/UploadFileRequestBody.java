package com.ipusoft.localcall.upload;

import com.ipusoft.localcall.UploadFileObserve;
import com.ipusoft.localcall.bean.UploadResponse;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * author : GWFan
 * time   : 4/9/21 11:55 AM
 * desc   : 文件上传的请求Body
 */

public class UploadFileRequestBody extends RequestBody {
    private static final String TAG = "UploadFileRequestBody";

    private final RequestBody mRequestBody;
    private final UploadFileObserve<UploadResponse> uploadFileObserve;

    public UploadFileRequestBody(String absolutePath, UploadFileObserve<UploadResponse> uploadFileObserve) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), new File(absolutePath));
        this.uploadFileObserve = uploadFileObserve;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(@NotNull BufferedSink sink) throws IOException {
        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        // 写入
        mRequestBody.writeTo(bufferedSink);
        // 刷新 ,必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    /**
     * CountingSink.
     */
    protected final class CountingSink extends ForwardingSink {
        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(@NotNull Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            if (uploadFileObserve != null) {
                uploadFileObserve.onProgressChange(bytesWritten, contentLength());
            }
        }
    }
}
