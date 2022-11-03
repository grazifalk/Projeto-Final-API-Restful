package br.org.serratec.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.org.serratec.dto.EmailDTO;
import br.org.serratec.exception.EmailException;
import br.org.serratec.model.ItemPedido;
import br.org.serratec.model.Pedido;
import br.org.serratec.model.Produto;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private JavaMailSender emailSender;

	private final String emailRemetente = "priscila.a.ferreira@aluno.senai.br";

	public void enviar(EmailDTO emailDTO) {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		try {
			helper.setFrom(emailDTO.getRemetente());
			helper.setSubject(emailDTO.getAssunto());
			helper.setText(emailDTO.getMensagem(), true);

			helper.setTo(emailDTO.getDestinatarios().toArray(new String[emailDTO.getDestinatarios().size()]));

			javaMailSender.send(mimeMessage);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public void sendMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(emailRemetente);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public void emailCompra(Pedido pedido) throws MessagingException, EmailException {

		List<ItemPedido> item = pedido.getItens();

		List<String> destinatarios = new ArrayList<>();
		destinatarios.add("grazifalk@gmail.com");
		destinatarios.add("queziamenezessouza@gmail.com");
		destinatarios.add("pritere90@gmail.com");
		destinatarios.add("priscila.a.ferreira@aluno.senai.br");
		destinatarios.add("roni.schanuel@docente.firjan.senai.br");

		// this.javaMailSender = emailSender();
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		DecimalFormat formato = new DecimalFormat("#.00");

		try {
			helper.setTo("priscila.a.ferreira@aluno.senai.br");
			helper.setTo("pritere90@gmail.com");
			helper.setTo("grazifalk@gmail.com");
			helper.setTo("queziamenezessouza@gmail.com");
			helper.setTo("roni.schanuel@docente.firjan.senai.br");
			helper.setSubject("Fênix Eletrônicos");

			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<html>");
			sBuilder.append("<body>");
			sBuilder.append("<div style=\"font-weight: 400; color: blue\">");
			sBuilder.append("<center>");
			sBuilder.append("<h1>Olá, " + pedido.getCliente().getNome() + "</h1>");
			sBuilder.append("</center>");
			sBuilder.append("<center>");
			sBuilder.append("<p style=\"text-align: justify\">Seu pedido de número: " + pedido.getIdPedido()
					+ " foi finalizado.</p>");
			for (ItemPedido itemPedido : item) {
				String valor = formato.format(itemPedido.getPrecoVenda());
				sBuilder.append(
						"<p style=\"text-align: justify\">Produto: " + itemPedido.getProduto().getNome() + ".</p>");
				sBuilder.append("<p style=\"text-align: justify\">Valor Unitário: "
						+ itemPedido.getProduto().getValorUnitario() + ".</p>");
				sBuilder.append("<p style=\"text-align: justify\">Quantidade: " + itemPedido.getQuantidade() + ".</p>");
				sBuilder.append("<p style=\"text-align: justify\">Total: " + valor + ".</p>");
			}
			sBuilder.append("<center style=\"opacity: 0.4\">");
			sBuilder.append("</center>");
			sBuilder.append("<div>");
			sBuilder.append("<p>Atenciosamente,</p> <br>");
			sBuilder.append("<p><em>Fênix Eletrônicos</em> <br>");
			sBuilder.append("<p><strong>Continue a voar!</strong><br>");
			sBuilder.append("</div>");
			sBuilder.append("<center>");
			sBuilder.append(
					"<p>E-mail automático. Caso já tenha respondido este email e enviado o seu formulário favor desconsiderar essa mensagem.</p>");
			sBuilder.append("</center>");
			sBuilder.append("</div>");
			sBuilder.append("</body>");
			sBuilder.append("</html>");
			sBuilder.append("");

			helper.setText(sBuilder.toString(), true);

			emailSender.send(message);
		} catch (Exception e) {
			throw new EmailException("Houver erro ao enviar o email" + e);

		}
	}

	public void emailEstoqueBaixo(List<Produto> listaEstoqueBaixo) throws MessagingException, EmailException {

		List<String> destinatarios = new ArrayList<>();
		destinatarios.add("grazifalk@gmail.com");
		destinatarios.add("queziamenezessouza@gmail.com");
		destinatarios.add("pritere90@gmail.com");
		destinatarios.add("priscila.a.ferreira@aluno.senai.br");
		destinatarios.add("roni.schanuel@docente.firjan.senai.br");

		// this.javaMailSender = emailSender();
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		try {
			helper.setTo("priscila.a.ferreira@aluno.senai.br");
			helper.setTo("pritere90@gmail.com");
			helper.setTo("grazifalk@gmail.com");
			helper.setTo("queziamenezessouza@gmail.com");
			helper.setTo("roni.schanuel@docente.firjan.senai.br");
			helper.setSubject("Estoque Baixo");

			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<html>");
			sBuilder.append("<body>");
			sBuilder.append("<div style=\"font-weight: 400; color: blue\">");
			sBuilder.append("<center>");
			sBuilder.append("<h1>Alerta de Estoque Baixo</h1>");
			sBuilder.append("</center>");
			sBuilder.append("<center>");
			for (Produto produto : listaEstoqueBaixo) {
				sBuilder.append("<p style=\"text-align: justify\">Produto: " + produto.getNome() + ".</p>");
				sBuilder.append(
						"<p style=\"text-align: justify\">Quantidade: " + produto.getQuantidadeEstoque() + ".</p>");
			}
			sBuilder.append("<center style=\"opacity: 0.4\">");
			sBuilder.append("</center>");
			sBuilder.append("<div>");
			sBuilder.append("<p>Atenciosamente,</p> <br>");
			sBuilder.append("<p><em>Fênix Eletrônicos</em> <br>");
			sBuilder.append("<p><strong>Continue a voar!</strong><br>");
			sBuilder.append("</div>");
			sBuilder.append("<center>");
			sBuilder.append(
					"<p>E-mail automático. Caso já tenha respondido este email e enviado o seu formulário favor desconsiderar essa mensagem.</p>");
			sBuilder.append("</center>");
			sBuilder.append("</div>");
			sBuilder.append("</body>");
			sBuilder.append("</html>");
			sBuilder.append("");

			helper.setText(sBuilder.toString(), true);

			emailSender.send(message);
		} catch (Exception e) {
			throw new EmailException("Houve erro ao enviar o email" + e);

		}
	}

	public void emailPedidoFinalizado(Pedido pedido) throws MessagingException, EmailException {

		List<ItemPedido> item = pedido.getItens();
		List<String> destinatarios = new ArrayList<>();
		destinatarios.add("grazifalk@gmail.com");
		destinatarios.add("queziamenezessouza@gmail.com");
		destinatarios.add("pritere90@gmail.com");
		destinatarios.add("roni.schanuel@docente.firjan.senai.br");

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		DecimalFormat formato = new DecimalFormat("#.00");

		helper.setTo("priscila.a.ferreira@aluno.senai.br");
		helper.setTo("pritere90@gmail.com");
		helper.setTo("grazifalk@gmail.com");
		helper.setTo("queziamenezessouza@gmail.com");
		helper.setTo("roni.schanuel@docente.firjan.senai.br");
		helper.setSubject("Pedido Finalizado");
		StringBuilder sBuilder = new StringBuilder();

		sBuilder.append("<html>");
		sBuilder.append("<body>");
		sBuilder.append("<div style=\"font-weight: 400; color: blue\">");
		sBuilder.append("<center>");
		sBuilder.append("<h1>Olá, " + pedido.getCliente().getNome() + "</h1>");
		sBuilder.append("</center>");
		sBuilder.append("<center>");
		sBuilder.append("<p style=\"text-align: justify\">Seu pedido de número: " + pedido.getIdPedido()
				+ " foi finalizado.</p>");
		for (ItemPedido itemPedido : item) {
			String valor = formato.format(itemPedido.getPrecoVenda());
			sBuilder.append("<p style=\"text-align: justify\">Produto: " + itemPedido.getProduto().getNome() + ".</p>");
			sBuilder.append("<p style=\"text-align: justify\">Valor Unitário: "
					+ itemPedido.getProduto().getValorUnitario() + ".</p>");
			sBuilder.append("<p style=\"text-align: justify\">Quantidade: " + itemPedido.getQuantidade() + ".</p>");
			sBuilder.append("<p style=\"text-align: justify\">Total: " + valor + ".</p>");
		}
		sBuilder.append("<center style=\"opacity: 0.4\">");
		sBuilder.append("</center>");
		sBuilder.append("<div>");
		sBuilder.append("<p>Atenciosamente,</p> <br>");
		sBuilder.append("<p><em>Fênix Eletrônicos</em> <br>");
		sBuilder.append("<p><strong>Continue a voar!</strong><br>");
		sBuilder.append("</div>");
		sBuilder.append("<center>");
		sBuilder.append(
				"<p>E-mail automático. Caso já tenha respondido este email e enviado o seu formulário favor desconsiderar essa mensagem.</p>");
		sBuilder.append("</center>");
		sBuilder.append("</div>");
		sBuilder.append("</body>");
		sBuilder.append("</html>");
		sBuilder.append("");

		helper.setText(sBuilder.toString(), true);

		emailSender.send(message);
	}
}