package org.example.testsendemail.serviceImp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.testsendemail.service.EmailService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.awt.image.BufferedImage;

@Service
@RequiredArgsConstructor
public class EmailServiceImp implements EmailService {
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    @SneakyThrows
    @Override
    public String sendEmail(String email) {
        String qrText = "00020101021129450016abaakhppxxx@abaa01090057580810208ABA Bank40390006abaP2P01129B44C11C847502090057580815204000053038405802KH5911KOUERN DOCH6010Phnom Penh63040196";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrText, BarcodeFormat.QR_CODE, 256, 256);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ImageIO.write(image, "png", baos);
        byte[] imageBytes = baos.toByteArray();


        Context context = new Context();
        context.setVariable("qrcode", "qr001");

        String process = templateEngine.process("index", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setSubject("Bill for Rent room");
        mimeMessageHelper.setText(process, true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.addInline("qr001",new ByteArrayResource(imageBytes),"image/png");

        javaMailSender.send(mimeMessage);
        return "success send";
    }

}
