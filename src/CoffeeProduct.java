import java.util.Objects;

/**
 * Generalized base class for coffee products.
 *
 * <p>All products include:</p>
 * <ul>
 *   <li>display name and brand</li>
 *   <li>weight in kilograms</li>
 *   <li>package volume in cubic meters (including packaging)</li>
 *   <li>price</li>
 *   <li>quality score</li>
 * </ul>
 *
 * <p>This class supports polymorphic usage and value-based equality for Lab6 Set uniqueness.</p>
 */
public abstract class CoffeeProduct
{
    private final String displayName;
    private final String brand;
    private final double weightKg;
    private final double packageVolumeM3;
    private final double price;
    private final int qualityScore;

    /**
     * Creates a coffee product.
     *
     * @param displayName human-readable name
     * @param brand product brand/origin label
     * @param weightKg net weight in kilograms
     * @param packageVolumeM3 package volume in cubic meters (including packaging)
     * @param price product price
     * @param qualityScore quality score (0..100 recommended)
     * @throws IllegalArgumentException if parameters are invalid
     */
    protected CoffeeProduct(
            String displayName,
            String brand,
            double weightKg,
            double packageVolumeM3,
            double price,
            int qualityScore)
    {
        if (displayName == null || displayName.isBlank())
        {
            throw new IllegalArgumentException("displayName must not be blank");
        }
        if (brand == null || brand.isBlank())
        {
            throw new IllegalArgumentException("brand must not be blank");
        }
        if (weightKg <= 0.0)
        {
            throw new IllegalArgumentException("weightKg must be > 0");
        }
        if (packageVolumeM3 <= 0.0)
        {
            throw new IllegalArgumentException("packageVolumeM3 must be > 0");
        }
        if (price <= 0.0)
        {
            throw new IllegalArgumentException("price must be > 0");
        }
        if (qualityScore < 0)
        {
            throw new IllegalArgumentException("qualityScore must be >= 0");
        }

        this.displayName = displayName;
        this.brand = brand;
        this.weightKg = weightKg;
        this.packageVolumeM3 = packageVolumeM3;
        this.price = price;
        this.qualityScore = qualityScore;
    }

    /**
     * @return product display name
     */
    public final String getDisplayName()
    {
        return displayName;
    }

    /**
     * @return brand/origin label
     */
    public final String getBrand()
    {
        return brand;
    }

    /**
     * @return net weight in kilograms
     */
    public final double getWeightKg()
    {
        return weightKg;
    }

    /**
     * @return package volume in cubic meters (including packaging)
     */
    public final double getPackageVolumeM3()
    {
        return packageVolumeM3;
    }

    /**
     * @return price
     */
    public final double getPrice()
    {
        return price;
    }

    /**
     * @return quality score
     */
    public final int getQualityScore()
    {
        return qualityScore;
    }

    /**
     * Price-to-weight ratio used for sorting in Lab5.
     *
     * @return price per kilogram
     */
    public final double getPriceToWeightRatio()
    {
        return price / weightKg;
    }

    /**
     * Returns a short human-readable product state description.
     *
     * @return state label
     */
    public abstract String getStateLabel();

    @Override
    public String toString()
    {
        return getClass().getSimpleName()
                + "{name='" + displayName + '\''
                + ", brand='" + brand + '\''
                + ", state='" + getStateLabel() + '\''
                + ", weightKg=" + weightKg
                + ", packageVolumeM3=" + packageVolumeM3
                + ", price=" + price
                + ", quality=" + qualityScore
                + '}';
    }

    /**
     * Two products are considered equal if their runtime class and base fields match
     * and the state label matches.
     *
     * @param o other object
     * @return true if equal by value
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        CoffeeProduct that = (CoffeeProduct) o;
        return Double.compare(weightKg, that.weightKg) == 0
                && Double.compare(packageVolumeM3, that.packageVolumeM3) == 0
                && Double.compare(price, that.price) == 0
                && qualityScore == that.qualityScore
                && displayName.equals(that.displayName)
                && brand.equals(that.brand)
                && getStateLabel().equals(that.getStateLabel());
    }

    /**
     * Hash code consistent with {@link #equals(Object)}.
     *
     * @return hash code
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(
                getClass(),
                displayName,
                brand,
                weightKg,
                packageVolumeM3,
                price,
                qualityScore,
                getStateLabel()
        );
    }
}
