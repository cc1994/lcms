package lcms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import lcms.struct.TreeNode;
import lcms.utils.TreePrintUtil;

public class TreeRelatedAnswer {
    //         6
    //     3       7
    //   2   4
    //  1     5
    
    public static void main(String[] args) {
        TreeRelatedAnswer answer = new TreeRelatedAnswer();
        TreeNode root = new TreeNode(6);
        
        TreeNode node1 = new TreeNode(3);
        TreeNode node11 = new TreeNode(2);
        node11.left = new TreeNode(1);
        node1.left = node11;
        TreeNode node12 = new TreeNode(5);
        node12.left = new TreeNode(4);
        node1.right = node12;
        root.left = node1;
        
        TreeNode node2 = new TreeNode(7);
        root.right = node2;
        
        int[] pre  = new int[] {6, 3, 2, 1, 4, 5, 7};
        int[] in   = new int[] {1, 2, 3, 4, 5, 6, 7};
        int[] post = new int[] {1, 2, 5, 4, 3, 7 ,6};
        int[] nums = new int[] {-10,-3,0,5,9};
        
//        List<Integer> findPath = answer.rightSideView2(root);
//        TreeNode tree = answer.buildBST(0, nums.length, nums);
        ArrayList<ArrayList<Integer>> findPath = answer.findPath(root, 13);
        System.out.println(findPath);
//        TreePrintUtil.pirnt(tree);
        
//        answer.levelOrderTraversal(root);

    }
    
    List<Integer> rtn = new ArrayList<Integer>();
    // 中序遍历（递归）
    public List<Integer> inorderTraversalRecursive(TreeNode root) {
        if (root == null) {
            return rtn;
        }
        inorderTraversalRecursive(root.left);
        rtn.add(root.val);
        inorderTraversalRecursive(root.right);
        return rtn;
    }

    // 中序遍历（循环）
    public List<Integer> inorderTraversalLoop(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            }
            if (root == null) {
                TreeNode pop = stack.pop();
                rtn.add(pop.val);
                root = pop.right;
            }
        }
        return rtn;
    }
    
    // 前序遍历（循环）
    public void preOrderTraversalLoop(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            if (root != null) {
                stack.push(root);
                rtn.add(root.val);
                root = root.left;
            }
            if (root == null) {
                TreeNode node = stack.pop();
                root = node.right;
            }
        }
    }

    // 后序遍历（递归）
    public void postOrderTraverseRecursive(TreeNode node) {
        if (node == null)
            return;
        postOrderTraverseRecursive(node.left);
        postOrderTraverseRecursive(node.right);
        System.out.println(node.val);
    }
    
    // 后序遍历（循环）
    public void postOrderTraversalLoop(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> output = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            if (root != null) {
                stack.push(root);
                output.push(root);
                root = root.right;
            }
            if (!stack.isEmpty()) {
                TreeNode pop = stack.pop();
                root = pop.left;
            }
        }
        while (!output.isEmpty()) {
            System.out.println(output.pop().val);
        }
    }
    
    // 寻找符合target的路径，中序遍历(循环)： 左 根 右
    Stack<Integer> subStack = new Stack<>();
    ArrayList<ArrayList<Integer>> array = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
        if (root == null ) {
            return array;
        }
        subStack.push(root.val);
        // 叶子节点且刚好为目标路径
        if (root.left == null && root.right == null && root.val == target) {
            ArrayList<Integer> arr = new ArrayList<>();
            for (int i = 0; i < subStack.size(); i++) {
                arr.add(subStack.get(i));
            }
            array.add(arr);
            subStack.pop();
            return array;
        }
        // 叶子节点且不为目标路径
        if (root.left == null && root.right == null && root.val != target) {
            subStack.pop();
            return array;
        }
        FindPath(root.left, target-root.val);
        FindPath(root.right, target-root.val);
        subStack.pop();
        return array;
    }
    
    ArrayList<Integer> list = new ArrayList<>(); // 记录一个解空间
    ArrayList<ArrayList<Integer>> paths = new ArrayList<>(); // 记录所有符合条件的解空间

    public ArrayList<ArrayList<Integer>> findPath(TreeNode root, int target) {
        if (root == null)
            return paths;
        list.add(root.val);
        target -= root.val;
        if (target == 0 && root.left == null && root.right == null) {
            paths.add(new ArrayList<Integer>(list)); // 有解，将其加入路径列表
        }
        findPath(root.left, target);
        findPath(root.right, target);
        list.remove(list.size() - 1); // 解空间返回上一层
        return paths;
    }

    
    // 层序遍历
    public void levelOrderTraversal(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.println(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }
    
    // 层序遍历1，按层打印(记录每层节点数量)
    public List<List<Integer>> levelOrderTraversal2(TreeNode root) {
        List<List<Integer>> rt = new ArrayList<List<Integer>>();
        if (root == null) {
            return rt;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int toBePrinted = 1;
        int nextLevel = 0;
        List<Integer> level = new ArrayList<>();
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            toBePrinted--;
            level.add(node.val);
            if (node.left != null) {
                queue.offer(node.left);
                nextLevel++;
            }
            if (node.right != null) {
                queue.offer(node.right);
                nextLevel++;
            }
            if (toBePrinted == 0) {
                // 注意创建新的list
                rt.add(new ArrayList<Integer>(level));
                level.clear();
                toBePrinted = nextLevel;
                nextLevel = 0;
            }
        }
        return rt;
    }
    
    // 层序遍历2，按层打印(加入特殊节点)
    public List<List<Integer>> levelOrderTraversal3(TreeNode root) {
        List<List<Integer>> rt = new ArrayList<List<Integer>>();
        if (root == null) {
            return rt;
        }
        
        final TreeNode end = new TreeNode(-1);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        queue.offer(end);
        
        List<Integer> level = new ArrayList<>();
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == end) {
                // 注意创建新的list
                rt.add(new ArrayList<Integer>(level));
                level.clear();
                if (!queue.isEmpty()) {
                    queue.offer(end);
                }
            } else {
                level.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
        }
        return rt;
    }
   
    // 打印所有的路径
    List<List<Integer>> rt = new ArrayList<>();
    Stack<Integer> path = new Stack<>();
    public List<List<Integer>> binaryTreePaths(TreeNode root) {
        if (root == null) {
            return rt;
        }
        path.push(root.val);
        if (root.left == null && root.right == null) {
            // 是一条路径
            rt.add(new ArrayList<Integer>(path));
            path.pop();
            return rt;
        }
        binaryTreePaths(root.left);
        binaryTreePaths(root.right);
        path.pop();
        return rt;
    }

    // 二叉树的右视图（其实就是打印每层的最后一个节点）,通过记录每层节点数量
    public List<Integer> rightSideView2(TreeNode root) {
        List<Integer> rtn = new ArrayList<>();
        if (root == null) {
            return rtn;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int toBePrinted = 1;
        int nextLevel = 0;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.left != null) {
                queue.offer(node.left);
                nextLevel++;
            }
            if (node.right != null) {
                queue.offer(node.right);
                nextLevel++;
            }
            
            if (toBePrinted == 1) {
                rtn.add(node.val);
            }
            toBePrinted--;
            if (toBePrinted == 0) {
                toBePrinted = nextLevel;
                nextLevel = 0;
            }
        }
        return rtn;
    }

    // 根据前序和中序遍历规则，构建树
    public TreeNode buildTreeByPreAndIn(int[] preOrders, int[] inOrders) {
        if (preOrders.length == 0 || inOrders.length == 0) {
            return null;
        }
        // 根节点
        TreeNode tree = new TreeNode(preOrders[0]);
        int index = search(0, inOrders.length, inOrders, tree.val);
        
//        Arrays.copyOfRange: 将一个原始的数组，从下标from开始复制，复制到上标to，生成一个新的数组。[form, to)
        tree.left = buildTreeByPreAndIn(Arrays.copyOfRange(preOrders, 1, index + 1), 
                Arrays.copyOfRange(inOrders, 0, index));
        tree.right = buildTreeByPreAndIn(Arrays.copyOfRange(preOrders, index + 1, preOrders.length),
                Arrays.copyOfRange(inOrders, index + 1, inOrders.length));
        return tree;
    }
    
    // 根据后序和中序遍历规则，构建树
    public TreeNode buildTreeByPostAndIn(int[] postOrders, int[] inOrders) {
        if (postOrders.length == 0 || inOrders.length == 0) {
            return null;
        }
        // 根节点
        TreeNode tree = new TreeNode(postOrders[postOrders.length - 1]);
        int index = search(0, inOrders.length, inOrders, tree.val);
        
        tree.left = buildTreeByPostAndIn(Arrays.copyOfRange(postOrders, 0, index), 
                Arrays.copyOfRange(inOrders, 0, index));
        tree.right = buildTreeByPostAndIn(Arrays.copyOfRange(postOrders, index, postOrders.length - 1),
                Arrays.copyOfRange(inOrders, index + 1, inOrders.length));
        return tree;
    }

    // 查找指定数据data在中序遍历重的位置。[left] root [right]
    private static int search(int start, int end, int[] inOrders, int data) {
        for (int i = start; i < end; i++) {
            if (data == inOrders[i]) {
                return i;
            }
        }
        return -1;
    }

    // 将有序数组转化为BST（中序遍历构建BST）
    TreeNode buildBST(int start, int end, int[] nums) {
        if (start >= end) {
            return null;
        }
        int mid = (start + end) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = buildBST(start, mid, nums);
        root.right = buildBST(mid + 1, end, nums);
        return root;
    }
    
    // 计算完全二叉树节点数量
    // 从根节点开始，查看右子树的高度right_h与左子树的高度left_h的关系，
    // 如果right_h <  left_h 说明右子树一定是满二叉树，左子树继续递归这个过程。
    // 如果right_h == left_h 说明左子树一定是满二叉树，右子树继续递归这个过程。
    // 对于满二叉树，有这个公式，如果树的高度为k，则其结点数为2^k - 1。
    public int countCompleteTreeNodes(TreeNode head) {
        if (head == null) {
            return 0;
        }
        return bs(head, 1, mostLeftLevel(head, 1));
    }
    
    private int bs(TreeNode node, int level, int h) {  // level表示当前结点的深度，h整棵树的深度
        if (level == h) {  
            return 1;
        }
        if (mostLeftLevel(node.right, level + 1) == h) {  // 表示左满
            return (1 << (h - level)) + bs(node.right, level + 1, h);  // 左子树结点个数+当前结点，右子树继续递归
        } else {
            return (1 << (h - level - 1)) + bs(node.left, level + 1, h);
        }
    }

    private int mostLeftLevel(TreeNode node, int level) {  // 求node结点的左子树深度，level当前结点深度
        while (node != null) {
            level++;
            node = node.left;
        }
        return level - 1;
    }
}