export const apiBase = process.env.NEXT_PUBLIC_API_BASE || 'http://localhost:8080';
export const apiKey = process.env.NEXT_PUBLIC_API_KEY || '';

// Default supported testnet chains (for UI hints)
export const supportedChains = {
  sepolia: 11155111,
  holesky: 17000,
} as const;
