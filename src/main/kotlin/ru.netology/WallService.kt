package ru.netology

object WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()

    fun clear() {
        posts = emptyArray()
        comments = emptyArray()
    }

    fun createComment(comment: Comment, postId: Int): Boolean {
        val post:Post? = try {
            findPostById(postId)
        }catch (e:PostNotFoundException){
            e.printStackTrace()
            return false
        }

        if (post != null) {
            comments += comment
            update(post.copy(comments = Comments(comments = comments)))
        }
        return true
    }

    private fun findPostById(postId: Int): Post?{
        for(post:Post in posts){
            if (post.id == postId) return post
        }
        throw PostNotFoundException()
    }

    fun add(post: Post): Post {
        val newPost = post.copy(id = posts.lastIndex + 1)
        posts += newPost
        return newPost
    }

    fun update(post: Post): Boolean {
        for ((index, updatingPost) in posts.withIndex()) {
            if (index == post.id) {
                posts[index] = post.copy(
                    ownerId = updatingPost.ownerId,
                    date = updatingPost.date
                )
                return true
            }
        }
        return false
    }
}