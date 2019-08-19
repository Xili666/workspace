package xyz.xili.workspace.bean;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class BaseBean implements Serializable {
    private Long id;
    private LocalDateTime createOn;
    private Long createBy;
    private LocalDateTime modifyOn;
    private Long modifyBy;
    private Long rowVersion;

    abstract String getTableName();
}
