package com.ElectronicStore.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order
{
    @Id
    private String orderId;

    private String orderStatus;  //Pending,Shipped,Delivered

    private String paymentStatus;  //NotPaid,Paid

    private Integer orderAmount;

    @Column(length = 1000)
    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderedDate;

    private Date deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
