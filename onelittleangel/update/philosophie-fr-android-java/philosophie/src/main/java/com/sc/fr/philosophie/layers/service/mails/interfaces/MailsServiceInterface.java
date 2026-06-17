package com.sc.fr.philosophie.layers.service.mails.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;

import java.util.List;

import io.reactivex.Observable;

public interface MailsServiceInterface extends MotherBusinessServiceInterface {

    /**
     *
     * @param mailTo
     * @return
     */
    Observable<Boolean> sendMailToAsync(List<String> mailTo, String emailSubject, String emailBody);
}
