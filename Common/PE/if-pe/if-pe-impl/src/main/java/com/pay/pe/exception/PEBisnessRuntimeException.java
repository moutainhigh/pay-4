package com.pay.pe.exception;



public class PEBisnessRuntimeException extends RuntimeException
{

    public PEBisnessRuntimeException()
    {
    }

    public PEBisnessRuntimeException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public PEBisnessRuntimeException(String message)
    {
        super(message);
    }

    public PEBisnessRuntimeException(Throwable cause)
    {
        super(cause);
    }

    public PEBisnessRuntimeException(ErrorCodeType errorCodeType)
    {
        super((new StringBuilder("errorCode:")).append(errorCodeType.getValue()).append("  errorMesg:").append(errorCodeType.getDesc()).toString());
        exceptionDetail = errorCodeType;
    }

    public PEBisnessRuntimeException(ErrorCodeType errorCodeType, String info)
    {
        super((new StringBuilder("errorCode:")).append(errorCodeType.getValue()).append("  errorMesg:").append(errorCodeType.getDesc()).append("   Info: ").append(info).toString());
        exceptionDetail = errorCodeType;
    }

    public ErrorCodeType getExceptionDetail()
    {
        return exceptionDetail;
    }

    public void setExceptionDetail(ErrorCodeType exceptionDetail)
    {
        this.exceptionDetail = exceptionDetail;
    }

    private static final long serialVersionUID = 0xe8f4b7dabf8fcdaL;
    private ErrorCodeType exceptionDetail;
}