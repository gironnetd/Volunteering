package com.sc.en.bouddhism.transverse.eventbus.events;

import com.sc.en.bouddhism.transverse.exceptions.ExceptionManaged;

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
