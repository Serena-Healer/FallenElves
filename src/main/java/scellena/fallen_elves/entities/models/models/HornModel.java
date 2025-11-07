package scellena.fallen_elves.entities.models.models;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;

public class HornModel extends ModelQuadruped {

	public HornModel() {
		super(12, 0.0F);
		textureWidth = 64;
		textureHeight = 32;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 5.0F, 2.0F);
		body.cubeList.add(new ModelBox(body, 24, 8, -6.0F, -10.0F, -7.0F, 12, 16, 8, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 6.0F, -8.0F);
		head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -8.0F, -6.0F, 8, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 0, -3.0F, -2.0F, -7.0F, 1, 2, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 0, 2.0F, -2.0F, -7.0F, 1, 2, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 33, 8, -2.0F, -10.0F, -8.0F, 4, 3, 4, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 34, 9, -2.0F, -12.0F, -9.0F, 4, 2, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 37, 10, -1.0F, -14.0F, -10.0F, 2, 2, 2, 0.0F, false));

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(-3.0F, 12.0F, 7.0F);
		leg1.cubeList.add(new ModelBox(leg1, 0, 16, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

		leg2 = new ModelRenderer(this);
		leg2.setRotationPoint(3.0F, 12.0F, 7.0F);
		leg2.cubeList.add(new ModelBox(leg2, 0, 16, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

		leg3 = new ModelRenderer(this);
		leg3.setRotationPoint(-3.0F, 12.0F, -5.0F);
		leg3.cubeList.add(new ModelBox(leg3, 0, 16, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

		leg4 = new ModelRenderer(this);
		leg4.setRotationPoint(3.0F, 12.0F, -5.0F);
		leg4.cubeList.add(new ModelBox(leg4, 0, 16, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));
	}

}