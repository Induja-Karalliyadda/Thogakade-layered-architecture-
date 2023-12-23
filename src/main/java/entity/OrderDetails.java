package entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetails {

    private String id;
    private String itemCode;
    private int qty;
    private double unitPrice;

}
