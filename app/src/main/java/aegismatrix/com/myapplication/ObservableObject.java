package aegismatrix.com.myapplication;

import java.util.Observable;

/**
 *
 */
public class ObservableObject extends Observable {
    private static ObservableObject instance;

    private ObservableObject() {
    }

    /**
     * @return
     */
    public static ObservableObject getInstance() {
        if (null == instance) {
            instance = new ObservableObject();
        }
        return instance;
    }

    /**
     * @param data
     */
    public void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}
