package com.arthuranthony.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.arthuranthony.cursomc.domain.Categoria;
import com.arthuranthony.cursomc.domain.Cidade;
import com.arthuranthony.cursomc.domain.Cliente;
import com.arthuranthony.cursomc.domain.Endereco;
import com.arthuranthony.cursomc.domain.Estado;
import com.arthuranthony.cursomc.domain.ItemPedido;
import com.arthuranthony.cursomc.domain.Pagamento;
import com.arthuranthony.cursomc.domain.PagamentoComBoleto;
import com.arthuranthony.cursomc.domain.PagamentoComCartao;
import com.arthuranthony.cursomc.domain.Pedido;
import com.arthuranthony.cursomc.domain.Produto;
import com.arthuranthony.cursomc.domain.enums.EstadoPagamento;
import com.arthuranthony.cursomc.domain.enums.TipoCliente;
import com.arthuranthony.cursomc.repositories.CategoriaRepository;
import com.arthuranthony.cursomc.repositories.CidadeRepository;
import com.arthuranthony.cursomc.repositories.ClienteRepository;
import com.arthuranthony.cursomc.repositories.EnderecoRepository;
import com.arthuranthony.cursomc.repositories.EstadoRepository;
import com.arthuranthony.cursomc.repositories.ItemPedidoRepository;
import com.arthuranthony.cursomc.repositories.PagamentoRepository;
import com.arthuranthony.cursomc.repositories.PedidoRepository;
import com.arthuranthony.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");	
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00d);
		Produto p2 = new Produto(null, "Impressora", 800.00d);
		Produto p3 = new Produto(null, "Mouse", 80.00d);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		Cliente cli1 = new Cliente(null, "Patrícia Sophie Aragão", "patriciasophiearagao_@gdsambiental.com.br", "765.052.875-82", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("(84) 2857-7172", "(84) 98269-4257"));
		
		Cliente cli2 = new Cliente(null, "Catarina Maya Rosa Teixeira", "catarinamayarosateixeira@sgstelecom.com.br", "835.638.475-33", TipoCliente.PESSOAFISICA);
		cli2.getTelefones().addAll(Arrays.asList("(21) 2882-7770", "(21) 98253-8235"));
		
		Endereco e1 = new Endereco(null, "Travessa Mônica", 456, "Apto 560", "Setor Morada do Sol 3 (Taquaralto)", "69309-215", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Guarujá", 848, "Apto 234", "Buritis", "77066-174", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p3.getItens().addAll(Arrays.asList(ip2));
		p2.getItens().addAll(Arrays.asList(ip3));
		
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));	
		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}
