package me.tapumandal.ovirupa.domain.blog;

import com.sun.istack.Nullable;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Created by TapuMandal on 11/7/2020.
 * For any query ask online.tapu@gmail.com
 */

@Data
public class BlogDto {

    @Nullable
    private int id;

    @NotNull
    @NotEmpty
    @Size(min=3, max = 50, message = "name field is not OK.")
    private String title;

    private String blog;

    private String productName;

    private String productId;

    private String blogImageIdentification;

    private Date createdAt;

    private boolean active = true;

    private boolean isDelete = false;
}
