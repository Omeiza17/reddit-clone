package dev.codingstoic.server.service

import dev.codingstoic.server.entity.NotificationEmail
import dev.codingstoic.server.execption.SpringRedditException
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import javax.mail.internet.MimeMessage


@Service
class MailService(val mailSender: JavaMailSender,
                  val mailContentBuilder: MailContentBuilder) {

    @Async // marks this method as one to be ran asynchronously
    fun sendMail(notificationEmail: NotificationEmail) {
        val messagePreparator = MimeMessagePreparator { mimeMessage: MimeMessage? ->
            val messageHelper = MimeMessageHelper(mimeMessage!!)
            messageHelper.setFrom("springreddit@email.com")
            messageHelper.setTo(notificationEmail.recipient)
            messageHelper.setSubject(notificationEmail.subject)
            messageHelper.setText(mailContentBuilder.build(notificationEmail.body))
        }
        try {
            mailSender.send(messagePreparator)
        } catch (e: MailException) {
            throw SpringRedditException("Exception occurred when sending mail to " + notificationEmail.recipient, e)
        }
    }
}
