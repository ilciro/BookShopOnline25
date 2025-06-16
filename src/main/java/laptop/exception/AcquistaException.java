package laptop.exception;

import java.io.Serial;

public class AcquistaException extends Exception{
    @Serial
    private static final long serialVersionUID = 1L;

    public AcquistaException(String mex)
    {
        super(mex);
    }
}
