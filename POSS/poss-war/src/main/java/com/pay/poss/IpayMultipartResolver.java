package com.pay.poss;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class IpayMultipartResolver extends CommonsMultipartResolver {

    /**
     * Parse the given servlet request, resolving its multipart elements.
     *
     * @param request the request to parse
     * @return the parsing result
     * @throws org.springframework.web.multipart.MultipartException if multipart resolution failed.
     */
    @Override
    protected MultipartParsingResult parseRequest(HttpServletRequest request)
            throws MultipartException {
        String encoding = determineEncoding(request);
        FileUpload fileUpload = prepareFileUpload(encoding);
        final HttpSession session = request.getSession();
        fileUpload.setProgressListener(new ProgressListener() {
            public void update(long pBytesRead, long pContentLength, int pItems) {
                int percent = (int) (((float) pBytesRead / (float) pContentLength) * 100);
                session.setAttribute("uploaded", percent + "%");
            }
        });

        return super.parseRequest(request);
    }

}
