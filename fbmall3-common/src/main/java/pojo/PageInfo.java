package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class PageInfo implements Serializable {
    // 分页查询时使用
    private int current; // 当前页
    private int size; // 每页存放的数据条数
    // 查看分页状态时使用
    private int currentPage; // 当前页页码
    private int pages; // 总页数
    private int pageSize; // 当前页面的数据条数
    private int total; // 查询到的所有数据条数
    private boolean hasNext; // 是否有下一页
    private boolean hasPrevious; // 是否有上一页
}
