package laptop.exception;

import java.io.Serial;

public class PersistenzaException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;


    public PersistenzaException(String mex)
    {
        super(mex);
    }
}
