public class Solution {
    public static void main(String args[]) {
        return maxProfit(new int[]{10, 40, 20, 30}, new int[]{60, 40, 100, 120}, 50));
    }

    static class Item {
        int wt, profit;
        Float profitProportion;

        public Item(int weight, int profit) {
            this.wt = weight;
            this.profit = profit;
            this.profitProportion = new Float((float)profit/weight);
        }

        public String toString() {
            return this.wt + " - " + this.profit + " - " + this.profitProportion;
        }
    }
    // Time & space : O(nlogn) & O(n) --- here greedy aproach works better than DP
    public static float maxProfit(int[] weights, int[] profits, int cap) {
        Item[] items = new Item[weights.length];  // O(n) space
        for(int i = 0 ; i < weights.length ; i++) {
            items[i] = new Item(weights[i], profits[i]);
        }
        Arrays.sort(items, (a,b) -> b.profitProportion.compareTo(a.profitProportion)); // O(nlogn)
        float totalProfit = 0f;
        for(Item i : items) { // O(n)
            if(i.wt <= cap) {
                totalProfit += (float)i.profit;
                cap -= i.wt;
                continue;
            }
            if(cap == 0)
                break;
            float itemFraction = (float)cap/i.wt;
            float profitFraction = itemFraction*i.profit;
            totalProfit += profitFraction;
            return totalProfit;
        }
        return totalProfit;
    }
}
