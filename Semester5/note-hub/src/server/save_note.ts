'use server'

import { executeQuery } from "@/db/query"
import { getUser } from "@/helper/user"

export const saveNote = async (id: number, save: boolean) => {
    const user = await getUser()
    await executeQuery( save ? 'save_note' : 'unsave_note', [user.id, id])
}