package lk.ijse.factory_management_system_te.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class OrderDetails {
    private String order_id;
    private String cus_id;
    private String cus_name;
    private String order_date;
    private Double order_amount;
    private String status;
}
