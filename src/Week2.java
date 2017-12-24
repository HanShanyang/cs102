import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Week2 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length ==  0) {
            return null;
        }
        int l = 0, r = nums.length - 1;
        TreeNode root = helper(nums, l, r);
        return root;
    }

    private TreeNode helper(int[] nums, int l, int r) {
        if (l > r) return null;
        int m = l + (r - l) / 2;
        TreeNode node = new TreeNode(nums[m]);
        node.left = helper(nums, l, m - 1);
        node.right = helper(nums, m + 1, r);
        return node;
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return res;
        }
        int u = 0, b = matrix.length - 1;
        int l = 0, r = matrix[0].length - 1;
        while (u <= b && l <= r) {
            // up left->right
            for (int i = l; i <= r; ++ i) {
                res.add(matrix[u][i]);
            }
            u ++;
            // right up->bottom
            for (int i = u; i <= b; ++ i) {
                res.add(matrix[i][r]);
            }
            r --;

            // bottom right->left
            if (u <= b) {
                for (int i = r; i >= l; -- i) {
                    res.add(matrix[b][i]);
                }
                b --;
            }

            // left bottom->up
            if (l <= r) {
                for (int i = b; i >= u; -- i) {
                    res.add(matrix[i][l]);
                }
                l ++;
            }
        }
        return res;
    }

    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int u = 0, b = n - 1;
        int l = 0, r = n - 1;
        int count = 0;
        while (u <= b && l <= r) {
            // up left->right
            for (int i = l; i <= r; ++ i) {
                res[u][i] = ++ count;
            }
            u ++;
            // right up->bottom
            for (int i = u; i <= b; ++ i) {
                res[i][r] = ++ count;
            }
            r --;

            // bottom right->left
            if (u <= b) {
                for (int i = r; i >= l; -- i) {
                    res[b][i] = ++ count;
                }
                b --;
            }

            // left bottom->up
            if (l <= r) {
                for (int i = b; i >= u; -- i) {
                    res[i][l] = ++ count;
                }
                l ++;
            }
        }
        return res;
    }

    public int[] merge(int[] arr) {
        // divide
        // conquer
        // merge
        int[] helper = new int[arr.length];
        partition(arr, helper,0, arr.length - 1);
        return arr;
    }
    private void partition(int[] arr, int[] helper, int left, int right) {
        if (left >= right) {
            return;
        }
        int m = left + (right - left) / 2;
        partition(arr, helper, left, m);
        partition(arr, helper,m + 1, right);
        doMerge(arr, helper, left, m, right);
    }

    private void doMerge(int[] arr, int[] helper, int left, int m, int right) {
        int l = left, r = m + 1;
        int index = 0;
        while (l <= m && r <= right) {
            if (arr[l] < arr[r]) {
                helper[index ++] = arr[l ++];
            } else {
                helper[index ++] = arr[r ++];
            }
        }
        //copy back
        for (int i = 0; i < index; ++ i) {
            arr[i + left] = helper[i];
        }
    }

    public boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int l = 0, r = nums.length - 1;
        while (l + 1 < r) {
            int m = l + (r - l) / 2;
            if (nums[m] > nums[l]) {
                // left mid incr
                if (nums[m] > target && nums[l] <= target) {
                    r = m;
                } else {
                    l = m;
                }
            } else if (nums[m] < nums[l]){
                if (nums[m] < target && nums[r] >= target) {
                    l = m;
                } else {
                    r = m;
                }
            } else {
                l ++;
            }
        }

        return nums[l] == target || nums[r] == target;
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offerLast(root);
        boolean leftRight = true;
        while (!queue.isEmpty()) {
            List<Integer> sub = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; ++ i) {
                TreeNode now = queue.pollFirst();
                if (now.left != null) queue.offerLast(now.left);
                if (now.right != null) queue.offerLast(now.right);
                if (leftRight) {
                    sub.add(now.val);
                } else {
                    sub.add(0, now.val);
                }
            }
            res.add(sub);
            leftRight = !leftRight;
        }
        return res;
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;
        TreeNode dummy = new TreeNode(-1);
        TreeNode pre = dummy;
        TreeNode cur = root;
        dummy.left = root;
        // find target TreeNode
        while (cur != null && cur.val != key) {
            pre = cur;
            if (cur.val > key) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        if (cur == null) return root;

        TreeNode target = cur;
        // has two children
        if (cur.left != null && cur.right != null) {
            // get the min val in right subtree
            pre = cur;
            cur = cur.right;
            while (cur.left != null) {
                pre = cur;
                cur = cur.left;
            }
        }
        target.val = cur.val;

        // has at most 1 children
        if (pre.left == cur) {
            pre.left = cur.left != null ? cur.left : cur.right;
        } else {
            pre.right = cur.left != null ? cur.left : cur.right;
        }
        return dummy.left;
    }
}