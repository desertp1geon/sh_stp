
package com.example.sh_stp.basic.concurency;

public interface TaskCallback<T> {

    void onComplete(T result);

    void onError(Fault fault);
}
