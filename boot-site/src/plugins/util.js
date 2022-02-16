export function treeNode(nodes, pid) {
    // 获取所有顶级节点
    let parentNodes = [];
    nodes.forEach(node => {
        if (node.pid === pid) {
            // 使用json copy，避免引用传递
            parentNodes.push(JSON.parse(JSON.stringify(node)));
        }
    });

    if (!parentNodes) {
        return parentNodes;
    }
    // 获取子节点
    parentNodes.sort((a, b) => a.seq - b.seq);
    parentNodes.forEach(node => {
        // 递归加载子节点
        let childrenNodes = treeNode(nodes, node.id);
        if (childrenNodes) {
            node.children = childrenNodes;
        }
    })
    return parentNodes;
}
