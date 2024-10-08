package com.thialy.desafio_api.model.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.thialy.desafio_api.model.entities.Order;
import com.thialy.desafio_api.model.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) throws Exception {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new Exception("Pedido não encontrado");
        }
    }

    //delimitando o banco a 10 itens
    private static final int MAX_ORDERS = 10;

    @Transactional
    public Order saveOrder(Order order) throws Exception {
        if (orderRepository.countOrders() >= MAX_ORDERS) {
            throw new Exception("Número máximo de pedidos atingido.");
        }

        // se a data não for preenchida, vai preencher com a data atual
        if (order.getDataCadastro() == null) {
            order.setDataCadastro(new Date());
        }

        // se a quantidade não for preenchida, vai preencher com 1
        if (order.getQuantidade() == null || order.getQuantidade() == 0) {
            order.setQuantidade(1);
        }

        // Multiplicando o valor unitário pela quantidade de itens
        BigDecimal valorUnitario = new BigDecimal(order.getValor_un());
        BigDecimal quantidade = new BigDecimal(order.getQuantidade());
        BigDecimal valorTotal = valorUnitario.multiply(quantidade);

        // Arredonda o valor total para 2 casas decimais
        valorTotal = valorTotal.setScale(2, RoundingMode.HALF_UP);
        order.setValor_total(valorTotal.doubleValue());

        // Aplicando desconto
        BigDecimal valorDesconto;

        if (order.getQuantidade() >= 5 && order.getQuantidade() < 10) {
            valorDesconto = valorTotal.multiply(new BigDecimal("0.95"));
        } else if (order.getQuantidade() >= 10) {
            valorDesconto = valorTotal.multiply(new BigDecimal("0.90"));
        } else {
            valorDesconto = valorTotal; // Nenhum desconto aplicado
        }

        // Arredonda para 2 casas decimais
        valorDesconto = valorDesconto.setScale(2, RoundingMode.HALF_UP);

        // Define o valor do desconto no objeto order
        order.setValor_desconto(valorDesconto.doubleValue());

        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrder(Long id, Order order) throws Exception {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order updatedOrder = existingOrder.get();
            updatedOrder.setNome(order.getNome());
            updatedOrder.setQuantidade(order.getQuantidade());
            updatedOrder.setValor_un(order.getValor_un());
            updatedOrder.setDataCadastro(order.getDataCadastro());

            // Atualiza o valor total
            updatedOrder.setValor_total(updatedOrder.getValor_un() * updatedOrder.getQuantidade());

            // Calcula e aplica o desconto
            BigDecimal valor = new BigDecimal(updatedOrder.getValor_total());
            BigDecimal valorDesconto;

            if (updatedOrder.getQuantidade() >= 5 && updatedOrder.getQuantidade() < 10) {
                valorDesconto = valor.multiply(new BigDecimal("0.95"));
            } else if (updatedOrder.getQuantidade() >= 10) {
                valorDesconto = valor.multiply(new BigDecimal("0.90"));
            } else {
                valorDesconto = valor;
            }

            // Arredonda para 2 casas decimais
            valorDesconto = valorDesconto.setScale(2, RoundingMode.HALF_UP);

            updatedOrder.setValor_desconto(valorDesconto.doubleValue());

            return orderRepository.save(updatedOrder);
        } else {
            throw new Exception("Pedido não encontrado.");
        }
    }

    @Transactional
    public void deleteOrder(Long id) throws Exception {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new Exception("Pedido não encontrado.");
        }
    }
}
