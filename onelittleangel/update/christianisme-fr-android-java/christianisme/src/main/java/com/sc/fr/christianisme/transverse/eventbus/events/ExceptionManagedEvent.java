package com.sc.fr.christianisme.transverse.eventbus.events;

import com.sc.fr.christianisme.transverse.exceptions.ExceptionManaged;

public class ExceptionManagedEvent {
    private ExceptionManaged exceptionManaged;

    public ExceptionManagedEvent(ExceptionManaged exceptionManaged) {
        this.exceptionManaged = exceptionManaged;
    }

    public ExceptionManaged getExceptionManaged() {
        return exceptionManaged;
    }

    public void setExceptionManaged(ExceptionManaged exceptionManaged) {
        this.exceptionManaged = exceptionManaged;
    }
}
