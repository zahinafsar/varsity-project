'use server'

import { executeQuery } from "@/db/query"
import { getUser } from "@/helper/user"

export const likeNote = async (id: number, like: boolean) => {
    const user = await getUser()
    await executeQuery(like ? 'like_note' : 'dislike_note', [user.id, id])
}