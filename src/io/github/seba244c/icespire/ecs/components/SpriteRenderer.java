package io.github.seba244c.icespire.ecs.components;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import org.joml.Vector3f;

import io.github.seba244c.icespire.ecs.Component;
import io.github.seba244c.icespire.graphics.Mesh;
import io.github.seba244c.icespire.graphics.Renderer;
import io.github.seba244c.icespire.graphics.Texture;

/**
 * @author Sebsa
 * @since 20-504
 */
public class SpriteRenderer extends Component {
	private Mesh mesh;
	private boolean hidden;
	
	public SpriteRenderer(Mesh mesh) {
		this.mesh = mesh;
	}
	
	public void update() {
		Renderer.tbrSprites.add(this);
	}
	
	public void renderSprite() {
		if(hidden)
			return;
		Texture texture = mesh.getMaterial().getTexture();
        if (texture != null) {
            // Activate firs texture bank
            glActiveTexture(GL_TEXTURE0);
            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, texture.getId());
        }

        // Draw the mesh
        glBindVertexArray(mesh.getVaoId());
        Vector3f trans = entity.getTransform().getPosition();
        if(trans.z!=0)
        	trans.z = 0;
        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

        // Restore state
        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void cleanup() {
		if(mesh.getMaterial().isTextured())
			mesh.getMaterial().getTexture().cleanup();
		mesh.cleanup();
	}

	public Mesh getMesh() {
		return mesh;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

	/**
	 * @return Wether the mesh is hidden or not, hidden objects is not rendered
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * Sets wether the mesh is hidden or not, hidden objects is not rendered
	 * @param hidden Hidden
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
}
