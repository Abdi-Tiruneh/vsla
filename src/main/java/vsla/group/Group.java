package vsla.group;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vsla.userManager.address.Address;
import vsla.userManager.user.Users;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "groups")
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "group_size", nullable = false)
    private Integer groupSize;

    @Column(name = "entry_fee", nullable = false)
    private BigDecimal entryFee;

    @OneToOne
    @JoinColumn(name = "group_admin_id")
    private Users groupAdmin;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void setGroupAdmin(Users groupAdmin) {
        if (groupAdmin != null && groupAdmin.getRole().getRoleName().equalsIgnoreCase("GROUP_ADMIN"))
            this.groupAdmin = groupAdmin;
        else
            // Handle the case where the user doesn't have the "admin" role.
            throw new IllegalArgumentException("The user must have the 'admin' role to be a group admin.");
    }
}
