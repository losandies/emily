import { ChatMessage } from '@/types';
import { Text, View } from 'react-native';

type ChatBoxProps = {
    messages: ChatMessage[];
};
export default function Chatbox({ messages }: ChatBoxProps) {
    return (
        <View>
            {messages.map((message) => (
                <View key={message.id} className='gap-3 p-4'>
                    <Text
                        className={`max-w-[85%] rounded-2xl px-4 py-3 ${message.role == 'user' ? 'self-end bg-orange-300 text-white text-base' : 'self-start text-base text-gray-900'}`}
                    >
                        {message.message}
                    </Text>
                </View>
            ))}
        </View>
    );
}
