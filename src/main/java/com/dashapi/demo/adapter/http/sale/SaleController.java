package com.dashapi.demo.adapter.http.sale;

import com.dashapi.demo.domain.model.Sale;
import com.dashapi.demo.domain.port.in.SaleUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleUseCase saleUseCase;

    public SaleController(SaleUseCase saleUseCase) {
        this.saleUseCase = saleUseCase;
    }

    record SaleItemRequest(
            @NotBlank String product,
            @Min(1) int quantity,
            @NotNull @Positive BigDecimal price,
            @NotBlank String method) {}

    record CreateSaleRequest(Long customerId, @NotEmpty List<SaleItemRequest> items) {}

    @GetMapping
    public List<Sale> list() {
        return saleUseCase.listAll();
    }

    @PostMapping
    public ResponseEntity<Sale> create(@Valid @RequestBody CreateSaleRequest req) {
        List<SaleUseCase.SaleItemCommand> items = req.items().stream()
                .map(i -> new SaleUseCase.SaleItemCommand(i.product(), i.quantity(), i.price(), i.method()))
                .toList();
        Sale created = saleUseCase.create(new SaleUseCase.CreateSaleCommand(req.customerId(), items));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
