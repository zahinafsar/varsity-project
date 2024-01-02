export type Note = {
  id: number;
  title: string;
  content: string;
  author_name: string;
  author_image: string;
  created_at: string;
  images: string;
  is_saved: 1 | 0;
  is_liked: 1 | 0;
  like_count: number;
};

export type User = {
  id: number;
  student_id: string;
  full_name: string;
  avatar: string;
  password: string;
};
