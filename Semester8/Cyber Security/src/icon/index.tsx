export const SVG = {
  threeDots: () => (
    <svg
      width="3"
      height="20"
      viewBox="0 0 3 20"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <rect width="3" height="3" rx="0.5" fill="white" />
      <rect y="8.18188" width="3" height="3" rx="0.5" fill="white" />
      <rect y="16.3635" width="3" height="3" rx="0.5" fill="white" />
    </svg>
  ),
  logo: () => (
    <svg
      width="48"
      height="48"
      viewBox="0 0 48 48"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <rect width="48" height="48" rx="24" fill="white" />
      <ellipse
        cx="23.9671"
        cy="23.8396"
        rx="4.71193"
        ry="4.57982"
        fill="#FF0000"
      />
      <rect
        x="28.3781"
        y="29.2163"
        width="4.94009"
        height="12.8048"
        rx="2.47004"
        transform="rotate(-60 28.3781 29.2163)"
        fill="#FF0000"
      />
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M34.0337 35.3993C34.2486 35.5234 34.2962 35.8256 34.1067 35.9857C31.3694 38.2982 27.831 39.6922 23.9671 39.6922C15.2845 39.6922 8.24597 32.6536 8.24597 23.9711C8.24597 15.2886 15.2845 8.25 23.9671 8.25C32.6496 8.25 39.6882 15.2886 39.6882 23.9711C39.6882 24.9313 39.6021 25.8715 39.4372 26.7842C39.3913 27.0383 39.0936 27.1516 38.87 27.0225L36.1575 25.4564C35.9756 25.3514 35.7897 25.2595 35.6009 25.1805C35.1087 24.9746 34.748 24.504 34.748 23.9705C34.748 18.0163 29.9212 13.1895 23.967 13.1895C18.0128 13.1895 13.186 18.0163 13.186 23.9705C13.186 29.9247 18.0128 34.7515 23.967 34.7515C25.6819 34.7515 27.3034 34.3511 28.7429 33.6386C29.5503 33.239 30.5409 33.3828 31.3211 33.8332L34.0337 35.3993Z"
        fill="black"
      />
    </svg>
  ),
  send: () => (
    <svg
      stroke="currentColor"
      fill="none"
      strokeWidth="2"
      viewBox="0 0 24 24"
      strokeLinecap="round"
      strokeLinejoin="round"
      className="h-4 w-4 mr-1"
      height="1.3em"
      width="1.3em"
      xmlns="http://www.w3.org/2000/svg"
    >
      <line x1="22" y1="2" x2="11" y2="13"></line>
      <polygon points="22 2 15 22 11 13 2 9 22 2"></polygon>
    </svg>
  ),
};
