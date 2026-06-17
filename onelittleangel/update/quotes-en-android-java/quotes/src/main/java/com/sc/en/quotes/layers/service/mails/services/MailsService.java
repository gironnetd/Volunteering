package com.sc.en.quotes.layers.service.mails.services;

import com.sc.en.quotes.layers.service.MotherBusinessService;
import com.sc.en.quotes.layers.service.ServiceManagerInterface;
import com.sc.en.quotes.layers.service.mails.GMail;
import com.sc.en.quotes.layers.service.mails.interfaces.MailsServiceInterface;
import com.sc.en.quotes.transverse.orms.realm.models.Account;

import java.util.List;

import io.reactivex.Observable;

public class MailsService extends MotherBusinessService implements MailsServiceInterface {

    private Account mailAccount;

    private Boolean isEmailSended;
    /**
     * Constructor
     *
     * @param srvManager
     */
    public MailsService(ServiceManagerInterface srvManager) {
        super(srvManager);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<Boolean> sendMailToAsync(List<String> mailTo, String emailSubject, String emailBody) {
        GMail androidEmail = new GMail( mailTo, emailSubject,
                emailBody);
//        try {
//            androidEmail.createEmailMessage();
//            androidEmail.sendEmail();
//            return Observable.just(true);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        new SendMailTask(mailTo, emailSubject, emailBody, new SendMailTask.Callbacks() {
//            @Override
//            public void isEmailSended(boolean result) {
//                if(result)
//                    isEmailSended = true;
//                else
//                    isEmailSended = false;
//
//            }
//        }).execute(
//        );

//        return Observable.from(new SendMailTask(mailTo, emailSubject, emailBody, new SendMailTask.Callbacks() {
//            @Override
//            public void isEmailSended(boolean result) {
//                if(result)
//                    isEmailSended = true;
//                else
//                    isEmailSended = false;
//
//            }
//        }).execute(
//        ));
        return null;
    }
}
