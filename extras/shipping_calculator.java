import java.util.*;

/**
 * PHASE 1: Fixed Rate Shipping
 * Goal: Calculate cost based on fixed per-unit pricing by country and product.
 */
class FixedShippingCalculator {
    // Structure: Country -> Product -> Per-unit Cost
    private final Map<String, Map<String, Long>> rates = new HashMap<>();

    public void addRate(String country, String product, long cost) {
        rates.computeIfAbsent(country, k -> new HashMap<>()).put(product, cost);
    }

    // TC: O(P) where P is number of products in order, SC: O(1)
    public long calculateCost(String country, Map<String, Integer> order) {
        if (!rates.containsKey(country)) throw new IllegalArgumentException("Unsupported country");
        
        long total = 0;
        Map<String, Long> countryRates = rates.get(country);

        for (Map.Entry<String, Integer> entry : order.entrySet()) {
            String product = entry.getKey();
            int quantity = entry.getValue();
            
            if (!countryRates.containsKey(product)) continue; // Or throw error based on policy
            total += (long) quantity * countryRates.get(product);
        }
        return total;
    }
}

/**
 * PHASE 2: Tiered Incremental Pricing
 * Goal: Split quantities across ranges (tiers) and sum costs tier-by-tier.
 * Example: Qty 5, Tiers [0-2 @100], [3+ @90] -> (2*100) + (3*90).
 */
class TieredShippingCalculator {
    static class Tier {
        int min; // inclusive
        int max; // exclusive (Integer.MAX_VALUE for "3+")
        long unitCost;

        Tier(int min, int max, long unitCost) {
            this.min = min;
            this.max = max;
            this.unitCost = unitCost;
        }
    }

    private final Map<String, Map<String, List<Tier>>> tieredRates = new HashMap<>();

    // TC: O(P * T) where T is avg tiers per product, SC: O(1)
    public long calculateCost(String country, Map<String, Integer> order) {
        long total = 0;
        for (var entry : order.entrySet()) {
            total += calculateProductCost(country, entry.getKey(), entry.getValue());
        }
        return total;
    }

    private long calculateProductCost(String country, String product, int quantity) {
        List<Tier> tiers = tieredRates.getOrDefault(country, Collections.emptyMap()).get(product);
        if (tiers == null) return 0;

        long productTotal = 0;
        for (Tier tier : tiers) {
            if (quantity <= tier.min) break;
            
            // Calculate how many units fall into this specific tier
            int unitsInTier = Math.min(quantity, tier.max) - tier.min;
            if (unitsInTier > 0) {
                productTotal += (long) unitsInTier * tier.unitCost;
            }
        }
        return productTotal;
    }
}

/**
 * PHASE 3: Mixed Pricing Models (Fixed + Incremental)
 * Goal: Support both "per-unit" and "flat-fee" tiers.
 * Validation: Normalizes tiers by sorting and checking for gaps/overlaps.
 */
class MixedShippingCalculator {
    enum TierType { INCREMENTAL, FIXED }

    static class MixedTier {
        int min;
        int max;
        long cost;
        TierType type;

        MixedTier(int min, int max, long cost, TierType type) {
            this.min = min;
            this.max = max;
            this.cost = cost;
            this.type = type;
        }
    }

    private final Map<String, Map<String, List<MixedTier>>> config = new HashMap<>();

    /**
     * TC: O(P * T), SC: O(1)
     * Strategy: Iterate through sorted tiers, applying flat fees once and 
     * per-unit costs for the quantity slice within that tier.
     */
    public long calculateCost(String country, Map<String, Integer> order) {
        long total = 0;
        for (var entry : order.entrySet()) {
            total += calculateProductCost(country, entry.getKey(), entry.getValue());
        }
        return total;
    }

    private long calculateProductCost(String country, String product, int quantity) {
        if (quantity <= 0) return 0;
        List<MixedTier> tiers = config.getOrDefault(country, Collections.emptyMap()).get(product);
        if (tiers == null) return 0;

        long productTotal = 0;
        for (MixedTier tier : tiers) {
            if (quantity <= tier.min) break;

            if (tier.type == TierType.FIXED) {
                // Fixed fee is applied once if the quantity reaches this tier
                productTotal += tier.cost;
            } else {
                // Incremental units within this boundary
                int unitsInTier = Math.min(quantity, tier.max) - tier.min;
                productTotal += (long) unitsInTier * tier.cost;
            }
        }
        return productTotal;
    }

    // Helper to ensure config is valid (Sorted, No Gaps, No Overlaps)
    public void validateAndAddTiers(String country, String product, List<MixedTier> tiers) {
        tiers.sort(Comparator.comparingInt(t -> t.min));
        int current = 0;
        for (MixedTier t : tiers) {
            if (t.min != current) throw new IllegalArgumentException("Gap or overlap detected");
            current = t.max;
        }
        config.computeIfAbsent(country, k -> new HashMap<>()).put(product, tiers);
    }
}
