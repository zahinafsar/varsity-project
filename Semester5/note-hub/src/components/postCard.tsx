"use client";

import { dateFormatter } from "@/helper/date";
import { likeNote } from "@/server/like_note";
import { saveNote } from "@/server/save_note";
import Image from "next/image";
import { useState } from "react";
import { FaRegHeart, FaBookmark, FaHeart, FaRegBookmark } from "react-icons/fa";
import { Carousel } from "react-responsive-carousel";

export const PostCard = ({
  id,
  author,
  title,
  createdAt,
  description,
  authorImage,
  images = [],
  isLiked,
  isSaved,
  likeCount,
}: {
  id: number;
  author: string;
  title: string;
  description: string;
  authorImage: string;
  createdAt: string;
  images: string[];
  isLiked?: 1 | 0;
  isSaved?: 1 | 0;
  likeCount?: number;
}) => {
  const [likes, setLikes] = useState(likeCount || 0);
  const [liked, setLiked] = useState(isLiked === 1);
  const [saved, setSaved] = useState(isSaved === 1);

  const toggleLike = () => {
    setLiked((l) => {
      if (l) {
        setLikes(likes - 1);
      } else {
        setLikes(likes + 1);
      }
      return !l;
    });
  };

  const onLike = async () => {
    toggleLike();
    try {
      await likeNote(id, !liked);
    } catch (error) {
      toggleLike();
    }
  };

  const onSave = async () => {
    setSaved((p) => !p);
    try {
      await saveNote(id, !saved);
    } catch (error) {
      setSaved((p) => !p);
    }
  };

  return (
    <div className="border-b border-slate-700 p-6">
      <div className="bg-slate-700 rounded-lg w-full">
        <div className="flex flex-col overflow-hidden">
          <div className="m-3">
            <div className="flex gap-3 items-center">
              <Image
                width={35}
                height={35}
                className="rounded-full"
                src={
                  authorImage ||
                  "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/1200px-User-avatar.svg.png"
                }
                alt="Author Image"
              />
              <div>
                <div className="text-sm font-bold">{author}</div>
                <div className="text-xs">{dateFormatter(createdAt)}</div>
              </div>
            </div>
            <div className="text-lg font-semibold text-slate-300 mt-3">
              {title}
            </div>
            <div className="text-sm text-slate-300 mt-3">{description}</div>
          </div>
          {images.length > 0 ? (
            <Carousel showThumbs={false} showIndicators={false}>
              {images.map((note, key) => (
                <img key={key} width={455} src={note} />
              ))}
            </Carousel>
          ) : null}
        </div>
        <div className="flex justify-between items-center p-6">
          <div className="flex items-center gap-2">
            <div onClick={onLike} className="btn btn-ghost btn-sm">
              {!liked ? <FaRegHeart /> : <FaHeart />}
            </div>
            <p className="font-bold">{likes}</p>
          </div>
          <button onClick={onSave} className="btn btn-ghost btn-sm">
            {!saved ? <FaRegBookmark /> : <FaBookmark />}
          </button>
        </div>
      </div>
    </div>
  );
};
