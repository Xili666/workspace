package xyz.xili.workspace.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 文本内容
 */

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Content extends BaseBean {
    public static final String TABLE_NAME = "t_content";
    private String content;

    public Content(String content) {
        this.content = content;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
