export type ChatMessage = {
    id: number;
    role: 'user' | 'assistant';
    message: string;
    error: string | null;
};

export type User = {
    userId: number;
    firstName: string;
    alias: string | null;
};
