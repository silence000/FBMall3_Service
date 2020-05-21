package pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageInfo implements Serializable {
    // 查看分页状态时使用
    private long currentPage; // 当前页页码
    private long pages; // 总页数
    private long pageSize; // 当前页面的数据条数
    private long total; // 查询到的所有数据条数
    private boolean hasNext; // 是否有下一页
    private boolean hasPrevious; // 是否有上一页
}
