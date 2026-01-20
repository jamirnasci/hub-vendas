package com.jamir.hubvendas.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamir.hubvendas.model.PaymentMethods;
import com.jamir.hubvendas.model.Product;
import com.jamir.hubvendas.model.Sale;
import com.jamir.hubvendas.model.SaleStatus;
import com.jamir.hubvendas.service.ProductService;
import com.jamir.hubvendas.service.SaleService;

@Controller
public class HomeController {
    @Autowired
    private SaleService ss;
    @Autowired
    private ProductService ps;

    @GetMapping("/home")
    public String page() {
        return "home";
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard(
            @RequestParam(name = "start", required = false) LocalDateTime start,
            @RequestParam(name = "end", required = false) LocalDateTime end) {
        HashMap<String, Object> res = new HashMap<>();
        start = start == null ? LocalDateTime.now().minusDays(30) : start;
        end = end == null ? LocalDateTime.now() : end;
        List<Sale> sales = ss.findByCreatedAt(start, end);
        List<Product> products = ps.findAll();

        int productsCount = 0;

        for (Product p : products) {
            productsCount += p.getQuantity();
        }

        BigDecimal faturamento = BigDecimal.ZERO;
        int aVista = 0;
        int consorcio = 0;
        int financiamento = 0;

        int concluida = 0;
        int pendente = 0;
        int reprovada = 0;

        for (Sale s : sales) {
            BigDecimal precoBase = s.getProduct().getPrice(); // já é BigDecimal

            BigDecimal desconto = BigDecimal.valueOf(s.getOff()) // off é double
                    .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);

            BigDecimal fator = BigDecimal.ONE.subtract(desconto);

            BigDecimal valorVenda = precoBase.multiply(fator);

            faturamento = faturamento.add(valorVenda);

            if (s.getPaymentMethod() == PaymentMethods.A_VISTA) {
                aVista += 1;
            } else if (s.getPaymentMethod() == PaymentMethods.CONSORCIO) {
                consorcio += 1;
            } else {
                financiamento += 1;
            }

            if (s.getStatus() == SaleStatus.CONCLUIDA) {
                concluida += 1;
            } else if (s.getStatus() == SaleStatus.PENDENTE) {
                pendente += 1;
            } else {
                reprovada += 1;
            }
        }

        res.put("totalSales", sales.size());
        res.put("productsCount", productsCount);
        res.put("faturamento", faturamento);

        res.put("aVista", aVista);
        res.put("consorcio", consorcio);
        res.put("financiamento", financiamento);

        res.put("concluida", concluida);
        res.put("pendente", pendente);
        res.put("reprovada", reprovada);
        return ResponseEntity.ok(res);
    }
}
