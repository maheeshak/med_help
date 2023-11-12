package lk.ijse.factory_management_system_te.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Order {
    private String cus_id;
    private String order_id;
    private String order_date;
    private String cus_name;
    private String status;
}
