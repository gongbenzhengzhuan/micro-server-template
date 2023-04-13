package utils.utils.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author Eric·Wang[王承]
 * @Date 2023-04-04 15:04:02
 * @Description 构建树形数据
 */
@SuppressWarnings("all")
public final class TreeUtil<T> {

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-04-06 15:04:30
     * @Description 树化节点数据集合
     * @Param[0] com.zyc.commons.utils.utils.tree.Schema<T> schema
     * @Return java.util.List<T>
     */
    public static<T> List<T> buildTree(Schema<T> schema) {
        // 需要树化的节点数据集合
        List<T> nodeList = schema.getNodeList();
        // 父级节点编码反射属性名称
        String nodeParentIdReflexName = schema.getNodeParentIdReflexName();
        // 节点下子节点集合反射属性名称
        String nodeChildrenListReflexName = schema.getNodeChildrenListReflexName();
        // 支持树形结构节点名称关键词模糊查询，为空时，代表查询所有树形节点数据
        String nodeKeyWord = schema.getNodeKeyWord();

        List<T> treeNodeList = nodeList.stream()
                .filter(node -> {
                    Integer parentId = (Integer) invoke(node, Way.GET, nodeParentIdReflexName);
                    // 筛选出首层级节点
                    if (0 == parentId) {
                        return true;
                    }
                    return false;
                })
                .map(nodeRoot -> {
                    List<T> treeList = buildTree(schema, nodeRoot, nodeList);
                    invoke(nodeRoot, Way.SET, nodeChildrenListReflexName, treeList);
                    return nodeRoot;
                })
                .collect(Collectors.toList());
        if (!StringUtils.isBlank(nodeKeyWord)) {
            // 模糊查询得到模糊查询后的数据树
            treeMatch(schema, treeNodeList, nodeKeyWord);
            return treeNodeList;
        }
        // 完整的树形数据
        return treeNodeList;
    }

    private static<T> List<T> buildTree(Schema<T> schema, T nodeRoot, List<T> nodeList) {
        // 节点编码反射属性名称
        String nodeIdReflexName = schema.getNodeIdReflexName();
        // 父级节点编码反射属性名称
        String nodeParentIdReflexName = schema.getNodeParentIdReflexName();
        // 节点下子节点集合反射属性名称
        String nodeChildrenListReflexName = schema.getNodeChildrenListReflexName();
        return nodeList.stream()
                .filter(node -> {
                    Integer id = (Integer) invoke(nodeRoot, Way.GET, nodeIdReflexName);
                    Integer parentId = (Integer) invoke(node, Way.GET, nodeParentIdReflexName);
                    return Objects.equals(parentId, id);
                })
                .map(root -> {
                    List<T> rootList = buildTree(schema, root, nodeList);
                    invoke(root, Way.SET, nodeChildrenListReflexName, rootList);
                    return root;
                })
                .collect(Collectors.toList());
    }

    private static<T> void treeMatch(Schema<T> schema, List<T> treeNodeList, String keyWord) {
        // 节点名称反射属性名称
        String nodeNameReflexName = schema.getNodeNameReflexName();
        // 节点下子节点集合反射属性名称
        String nodeChildrenListReflexName = schema.getNodeChildrenListReflexName();
        Iterator<T> childrenNodeIterator = treeNodeList.iterator();
        while (childrenNodeIterator.hasNext()) {
            //当前节点
            T childrenNode = childrenNodeIterator.next();
            String nodeName = (String) invoke(childrenNode, Way.GET, nodeNameReflexName);
            if (!nodeName.contains(keyWord)) {
                // 当前节点不包含关键字，继续遍历下一级
                // 取出下一级节点
                List<T> childrenTreeNodeList = (List<T>) invoke(childrenNode, Way.GET, nodeChildrenListReflexName);
                // 递归
                if (null != childrenTreeNodeList && 0 < childrenTreeNodeList.size()) {
                    treeMatch(schema, childrenTreeNodeList, keyWord);
                }
                // 下一级节点都被移除了，那么父节点也移除，因为父节点也不包含关键字
                List<T> removeTreeNodeList = (List<T>) invoke(childrenNode, Way.GET, nodeChildrenListReflexName);
                if (null == removeTreeNodeList || 0 >= removeTreeNodeList.size()) {
                    childrenNodeIterator.remove();
                }
            } else {
                // 当前节点包含关键字，继续递归遍历
                // 子节点递归如果不包含关键字则删除
                List<T> childrenTreeNodeList = (List<T>) invoke(childrenNode, Way.GET, nodeChildrenListReflexName);
                // 递归
                if (null != childrenTreeNodeList && 0 < childrenTreeNodeList.size()) {
                    treeMatch(schema, childrenTreeNodeList, keyWord);
                }
            }
        }
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-04-06 16:04:26
     * @Description 反射调用get和set方法
     * @Param[0] T node 节点对象
     * @Param[1] java.lang.String reflexNamePrefix 反射属性名称前缀（get或者set）
     * @Param[2] java.lang.String reflexName 反射属性名称
     * @Param[3] java.lang.Object arg 参数
     * @Return java.lang.Object
     */
    private static<T> Object invoke(Object... args) {
        Parameter<T> parameter = buildParameter(args);
        try {
            Way reflexNamePrefix = parameter.getReflexNamePrefix();
            // 属性名称首字母大写
            String headUpperName = parameter.getReflexName().substring(0, 1).toUpperCase();
            // 属性首字母后的名称
            String secondName = parameter.reflexName.substring(1);
            // 拼接方法名称
            String invokeMethodName = reflexNamePrefix.name().toLowerCase() + headUpperName + secondName;
            Method method = null;
            if (Way.GET == reflexNamePrefix) {
                // 获取GET方法对象
                method = parameter.getNode().getClass().getMethod(invokeMethodName);
            } else {
                // 获取SET方法对象
                method = parameter.getNode().getClass().getMethod(invokeMethodName, List.class);
            }
            // 暴力反射
            method.setAccessible(true);
            return method.invoke(parameter.getNode(), parameter.getArg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-04-06 18:04:25
     * @Description 构建反射参数
     * @Param[0] java.lang.Object... args
     * @Return com.zyc.commons.utils.utils.tree.TreeUtil<T>.Parameter<T>
     */
    private static<T> Parameter<T> buildParameter(Object... args) {
        Parameter<T> parameter = new Parameter<>();
        parameter.setNode((T) args[0]);
        parameter.setReflexNamePrefix((Way) args[1]);
        parameter.setReflexName((String) args[2]);
        if (3 < args.length) {
            parameter.setArg(new Object[]{args[3]});
        }
        return parameter;
    }

    enum Way {
        SET,
        GET;

        public static void main(String[] args) {
            System.out.println(Way.GET.name().toLowerCase());
        }
    }

    /**
     * @Author Eric.Wang[王承]
     * @Date 2023-04-06 14:40 星期四
     * @ClassName com.zyc.commons.utils.utils.tree.Parameter
     * @Description: 反射入参
     */
    @SuppressWarnings("all")
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class Parameter<T> {

        /**
         * 节点对象
         */
        private T node;

        /**
         * 反射属性名称前缀（get或者set）
         */
        private Way reflexNamePrefix;

        /**
         * 反射属性名称
         */
        private String reflexName;

        /**
         * 方法反射入参参数
         */
        private Object[] arg = new Object[]{};

        @Override
        public String toString() {
            return "Parameter{" +
                    "[T]node=" + node +
                    ", [Way]reflexNamePrefix=" + reflexNamePrefix +
                    ", [String]reflexName=" + reflexName +
                    ", [Object[]]arg=" + arg +
                    "}";
        }
    }
}
