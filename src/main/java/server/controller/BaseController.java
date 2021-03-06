package server.controller;

import base.Constants;
import base.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @author guyue
 * @date 2018/10/15
 */
public abstract class BaseController extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest req) {
        Logger.system.info("message {} to {} from {}",
                req.method(), req.uri(), ctx.channel().remoteAddress());
        FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        HttpHeaders headers = res.headers();
        headers.add(HttpHeaderNames.CONTENT_TYPE, Constants.HTML_HEADER_VALUE_HTML);
        headers.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        doResponse(req, res);
        headers.add(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(res.content().readableBytes()));
        ctx.write(res);
    }

    /**
     * handling http request and return response
     * @param request request from browser
     * @param response response to browser
     */
    abstract void doResponse(FullHttpRequest request, FullHttpResponse response);

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Logger.system.error("exception caught: {}", cause);
        if (null != ctx) {
            ctx.close();
        }
    }
}
