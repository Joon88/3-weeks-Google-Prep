/*
This is an implementation of LIS(longest increasing subsequence)/LDS(longest decreasing subsequence)
(https://practice.geeksforgeeks.org/problems/box-stacking/1)

Another implementation (box-stacking) : LC-1691 | https://leetcode.com/problems/maximum-height-by-stacking-cuboids/
 */

// O(n^2) time and O(n) space
class Solution
{
    static class Box {
        int h, w, l;
        public Box(int h, int w, int l) {
            this.h = h;
            this.w = w;
            this.l = l;
        }
    }
    public static int maxHeight(int[] height, int[] width, int[] length, int n)
    {
        Box[] boxes = new Box[3*n];
        int idx = 0;
        for(int i = 0 ; i < n ; i++) {
            int h = height[i];
            int w = Math.max(width[i], length[i]);
            int l = Math.min(width[i], length[i]);
            boxes[idx++] = new Box(h, w, l);

            h = width[i];
            w = Math.max(height[i], length[i]);
            l = Math.min(height[i], length[i]);
            boxes[idx++] = new Box(h, w, l);

            h = length[i];
            w = Math.max(height[i], width[i]);
            l = Math.min(height[i], width[i]);
            boxes[idx++] = new Box(h, w, l);
        }

        Arrays.sort(boxes, (b1, b2) -> {
            int area1 = b1.l * b1.w;
            int area2 = b2.l * b2.w;

            return area2-area1;
        });

        // Find longest decreasing subsequence
        int LDSht[] = new int[boxes.length];
        for(int i = 0 ; i < boxes.length ; i++) {
            LDSht[i] = boxes[i].h;
        }

        for(int i = 1 ; i < boxes.length ; i++) {
            int j = 0;
            while(j < i) {
                if((boxes[j].w > boxes[i].w) && (boxes[j].l > boxes[i].l)) {
                    int one = LDSht[i];
                    int two = boxes[i].h + LDSht[j];
                    LDSht[i] = Math.max(one, two);
                }
                j++;
            }
        }
        return (Arrays.stream(LDSht).max().getAsInt());
    }
}
