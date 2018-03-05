package cyf.blog.dao.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 
 */
@Data

public class User implements Serializable {

    private Integer id;

    private String username;

    private String pwd;


    private static final long serialVersionUID = 1L;

}