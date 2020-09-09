package objectUtils;
import java.util.*;

public class Product extends ArrayList<Product> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
	protected Double price;
	protected String description;
	protected String img;

	public Product() {
		super();
	}
	
	public Product(String name, Double price, String description, String img) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.img = img;
	}

	public Product(String name, Double price) {
		super();
		this.name = name;
		this.price = price;
		this.description = "Description not available";
		this.img = "Image not available";
	}

	public Product(String name, Double price, String description) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.img = "Image not available";
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (img == null) {
			if (other.img != null)
				return false;
		} else if (!img.equals(other.img))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return name + " " + String.valueOf(price) + " " + description;
	}
	
	
}
