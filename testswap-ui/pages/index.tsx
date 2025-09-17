import Head from 'next/head'
import { useEffect, useState } from 'react'
import { apiBase } from '../src/lib/config'
import Link from 'next/link'
import Layout from '../src/components/Layout'

export default function Home() {
  const [apiStatus, setApiStatus] = useState<string>('loading...')

  useEffect(() => {
    const controller = new AbortController()
    ;(async () => {
      try {
        const res = await fetch(`${apiBase}/api/ping`, { signal: controller.signal })
        if (!res.ok) throw new Error(`HTTP ${res.status}`)
        const data = await res.json()
        setApiStatus(`API OK: ${data.message}`)
      } catch (err: any) {
        setApiStatus(`API 未连接：${err?.message || 'unknown error'}`)
      }
    })()
    return () => controller.abort()
  }, [])

  return (
    <>
      <Head>
        <title>TestSwap UI</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
      </Head>
      <Layout>
        <div className="space-y-6">
          <div>
            <h1 className="text-3xl font-bold tracking-tight">TestSwap UI</h1>
            <p className="mt-1 text-slate-600">MVP 功能导航</p>
          </div>

          <section>
            <h2 className="text-base font-semibold text-slate-900">后端连接状态</h2>
            <div className="mt-2 rounded-lg bg-white p-4 shadow-sm ring-1 ring-slate-200">
              {apiStatus}
            </div>
            <p className="mt-2 text-sm text-slate-600">
              默认后端 <code className="font-mono text-sm">{apiBase}</code>，本页请求 <code className="font-mono text-sm">/api/ping</code>。
            </p>
          </section>

          <section>
            <h2 className="text-base font-semibold text-slate-900">阶段 1：MVP 测试版</h2>
            <ul className="mt-2 grid gap-2 sm:grid-cols-2">
              <li>
                <Link href="/wallet" className="block rounded-lg bg-white p-4 shadow-sm ring-1 ring-slate-200 hover:shadow transition">
                  <div className="font-medium">钱包连接</div>
                  <div className="text-sm text-slate-600">连接 Sepolia / Holesky 测试网</div>
                </Link>
              </li>
              <li>
                <Link href="/swap" className="block rounded-lg bg-white p-4 shadow-sm ring-1 ring-slate-200 hover:shadow transition">
                  <div className="font-medium">测试网 Swap 工具</div>
                  <div className="text-sm text-slate-600">输入 Token 地址，选择费率</div>
                </Link>
              </li>
              <li>
                <Link href="/console" className="block rounded-lg bg-white p-4 shadow-sm ring-1 ring-slate-200 hover:shadow transition">
                  <div className="font-medium">合约交互控制台</div>
                  <div className="text-sm text-slate-600">call / sendTransaction</div>
                </Link>
              </li>
              <li>
                <Link href="/deploy-erc20" className="block rounded-lg bg-white p-4 shadow-sm ring-1 ring-slate-200 hover:shadow transition">
                  <div className="font-medium">部署 ERC20</div>
                  <div className="text-sm text-slate-600">name / symbol / supply</div>
                </Link>
              </li>
            </ul>
          </section>

          <section>
            <h2 className="text-base font-semibold text-slate-900">阶段 2：功能增强（预览）</h2>
            <ul className="mt-2 grid gap-2 sm:grid-cols-2">
              <li>
                <Link href="/forks" className="block rounded-lg bg-white p-4 shadow-sm ring-1 ring-slate-200 hover:shadow transition">
                  <div className="font-medium">Fork 测试管理器</div>
                  <div className="text-sm text-slate-600">创建/销毁/列表</div>
                </Link>
              </li>
              <li>
                <Link href="/trace" className="block rounded-lg bg-white p-4 shadow-sm ring-1 ring-slate-200 hover:shadow transition">
                  <div className="font-medium">Gas / Trace 分析</div>
                  <div className="text-sm text-slate-600">调试交易详情</div>
                </Link>
              </li>
              <li>
                <Link href="/pools" className="block rounded-lg bg-white p-4 shadow-sm ring-1 ring-slate-200 hover:shadow transition">
                  <div className="font-medium">部署池子 + 策略模拟</div>
                  <div className="text-sm text-slate-600">后续实现</div>
                </Link>
              </li>
              <li>
                <Link href="/scripts" className="block rounded-lg bg-white p-4 shadow-sm ring-1 ring-slate-200 hover:shadow transition">
                  <div className="font-medium">导出调用脚本（SDK）</div>
                  <div className="text-sm text-slate-600">后续实现</div>
                </Link>
              </li>
            </ul>
          </section>
        </div>
      </Layout>
    </>
  )
}
