package lk.ijse.factory_management_system_te.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderSummaryTM {
    private String order_id;
    private Double amount;
    private String date;
    private String status;
}
