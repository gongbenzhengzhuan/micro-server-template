package utils.utils.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Eric.Wang[王承]
 * @Date 2023-04-06 14:40 星期四
 * @ClassName com.zyc.commons.utils.utils.tree.Schema
 * @Description: 节点数据集合树化入参
 */
@SuppressWarnings("all")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Schema<T> {

    /**
     * 需要树化的节点数据集合
     */
    private List<T> nodeList;

    /**
     * 支持树形结构节点名称关键词模糊查询，为空时，代表查询所有树形节点数据
     */
    private String nodeKeyWord;

    /**
     * 节点ID中文名称反射属性名称
     */
    private String nodeNameReflexName;

    /**
     * 节点ID反射属性名称
     */
    private String nodeIdReflexName;

    /**
     * 父级节点ID反射属性名称
     */
    private String nodeParentIdReflexName;

    /**
     * 节点子类集合反射属性名称
     */
    private String nodeChildrenListReflexName;

    /**
     * 需要树化的节点数据集合
     */
    public Schema<T> setNodeList(List<T> nodeList) {
        this.nodeList = nodeList;
        return this;
    }

    /**
     * 支持树形结构节点名称关键词模糊查询，为空时，代表查询所有树形节点数据
     */
    public Schema<T> setNodeKeyWord(String nodeKeyWord) {
        this.nodeKeyWord = nodeKeyWord;
        return this;
    }

    /**
     * 节点ID中文名称反射属性名称
     */
    public Schema<T> setNodeNameReflexName(String nodeNameReflexName) {
        this.nodeNameReflexName = nodeNameReflexName;
        return this;
    }

    /**
     * 节点ID反射属性名称
     */
    public Schema<T> setNodeIdReflexName(String nodeIdReflexName) {
        this.nodeIdReflexName = nodeIdReflexName;
        return this;
    }

    /**
     * 父级节点ID反射属性名称
     */
    public Schema<T> setNodeParentIdReflexName(String nodeParentIdReflexName) {
        this.nodeParentIdReflexName = nodeParentIdReflexName;
        return this;
    }

    /**
     * 节点子类集合反射属性名称
     */
    public Schema<T> setNodeChildrenListReflexName(String nodeChildrenListReflexName) {
        this.nodeChildrenListReflexName = nodeChildrenListReflexName;
        return this;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "[List<T>]nodeList=" + nodeList +
                ", [String]nodeKeyWord=" + nodeKeyWord +
                ", [String]nodeNameReflexName=" + nodeNameReflexName +
                ", [String]nodeIdReflexName=" + nodeIdReflexName +
                ", [String]nodeParentIdReflexName=" + nodeParentIdReflexName +
                ", [String]nodeChildrenListReflexName=" + nodeChildrenListReflexName +
                "}";
    }
}
