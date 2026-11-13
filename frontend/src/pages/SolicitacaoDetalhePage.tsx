import { useCallback, useEffect, useState, type FormEvent } from "react";
import { Link, useParams } from "react-router-dom";
import {
  buscarSolicitacao,
  listarHistorico,
  mudarStatus,
} from "../api/solicitacoes";
import { ApiError } from "../api/client";
import type { HistoricoStatus, Solicitacao, StatusSolicitacao } from "../types/solicitacao";

const STATUS_OPCOES: StatusSolicitacao[] = [
  "ABERTA",
  "EM_ANDAMENTO",
  "RESOLVIDA",
];

function formatarData(iso: string): string {
  const d = new Date(iso);
  if (Number.isNaN(d.getTime())) {
    return iso;
  }
  return d.toLocaleString("pt-BR");
}

function ordenarHistorico(items: HistoricoStatus[]): HistoricoStatus[] {
  return [...items].sort(
    (a, b) => new Date(a.data).getTime() - new Date(b.data).getTime(),
  );
}

export function SolicitacaoDetalhePage() {
  const { id } = useParams<{ id: string }>();
  const [solicitacao, setSolicitacao] = useState<Solicitacao | null>(null);
  const [historico, setHistorico] = useState<HistoricoStatus[]>([]);
  const [carregando, setCarregando] = useState(true);
  const [erroCarregar, setErroCarregar] = useState<string | null>(null);

  const [novoStatus, setNovoStatus] = useState<StatusSolicitacao>("EM_ANDAMENTO");
  const [observacao, setObservacao] = useState("");
  const [erroStatus, setErroStatus] = useState<string | null>(null);
  const [enviando, setEnviando] = useState(false);

  const carregar = useCallback(async () => {
    if (!id) {
      return;
    }
    setCarregando(true);
    setErroCarregar(null);
    try {
      const [sol, hist] = await Promise.all([
        buscarSolicitacao(id),
        listarHistorico(id),
      ]);
      setSolicitacao(sol);
      setHistorico(ordenarHistorico(hist));
      setNovoStatus(sugerirProximoStatus(sol.status));
    } catch (e) {
      const msg =
        e instanceof ApiError ? e.message : "Não foi possível carregar.";
      setErroCarregar(msg);
      setSolicitacao(null);
      setHistorico([]);
    } finally {
      setCarregando(false);
    }
  }, [id]);

  useEffect(() => {
    void carregar();
  }, [carregar]);

  async function enviarMudancaStatus(e: FormEvent) {
    e.preventDefault();
    if (!id) {
      return;
    }
    setErroStatus(null);
    setEnviando(true);
    try {
      const atualizada = await mudarStatus(id, {
        status: novoStatus,
        observacao: observacao.trim() || undefined,
      });
      setSolicitacao(atualizada);
      const hist = await listarHistorico(id);
      setHistorico(ordenarHistorico(hist));
      setObservacao("");
      setNovoStatus(sugerirProximoStatus(atualizada.status));
    } catch (e) {
      setErroStatus(
        e instanceof ApiError
          ? e.message
          : "Não foi possível alterar o status.",
      );
    } finally {
      setEnviando(false);
    }
  }

  if (!id) {
    return <p className="text-red-600">ID inválido na URL.</p>;
  }

  if (carregando) {
    return <p className="text-slate-500">Carregando solicitação…</p>;
  }

  if (erroCarregar) {
    return (
      <div className="space-y-4">
        <p className="rounded-lg bg-red-50 px-4 py-3 text-red-800">
          {erroCarregar}
        </p>
        <Link to="/" className="text-sm text-emerald-700 hover:underline">
          ← Voltar ao início
        </Link>
      </div>
    );
  }

  if (!solicitacao) {
    return null;
  }

  return (
    <div className="space-y-8">
      <div>
        <Link to="/" className="text-sm text-emerald-700 hover:underline">
          ← Início
        </Link>
        <h1 className="mt-2 text-2xl font-semibold">{solicitacao.titulo}</h1>
        <p className="mt-1 text-sm text-slate-500">ID: {solicitacao.id}</p>
      </div>

      <section className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
        <dl className="grid gap-3 text-sm sm:grid-cols-2">
          <div>
            <dt className="text-slate-500">Status atual</dt>
            <dd className="font-medium">{solicitacao.status}</dd>
          </div>
          <div>
            <dt className="text-slate-500">Categoria</dt>
            <dd>{solicitacao.categoria}</dd>
          </div>
          <div>
            <dt className="text-slate-500">Bairro</dt>
            <dd>{solicitacao.bairro}</dd>
          </div>
          <div>
            <dt className="text-slate-500">Cidadão</dt>
            <dd className="font-mono text-xs">{solicitacao.cidadaoId}</dd>
          </div>
        </dl>
        <p className="mt-4 text-slate-700">{solicitacao.descricao}</p>
      </section>

      <section className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
        <h2 className="text-lg font-medium">Mudar status</h2>
        <form onSubmit={enviarMudancaStatus} className="mt-4 space-y-4">
          <label className="block text-sm">
            <span className="text-slate-600">Novo status</span>
            <select
              value={novoStatus}
              onChange={(e) =>
                setNovoStatus(e.target.value as StatusSolicitacao)
              }
              className="mt-1 w-full rounded-lg border border-slate-300 px-3 py-2"
            >
              {STATUS_OPCOES.map((s) => (
                <option key={s} value={s}>
                  {s}
                </option>
              ))}
            </select>
          </label>
          <label className="block text-sm">
            <span className="text-slate-600">Observação</span>
            <textarea
              value={observacao}
              onChange={(e) => setObservacao(e.target.value)}
              rows={3}
              className="mt-1 w-full rounded-lg border border-slate-300 px-3 py-2"
              placeholder="Opcional; a API usa um texto padrão se vazio"
            />
          </label>
          {erroStatus && (
            <p className="rounded-lg bg-amber-50 px-3 py-2 text-sm text-amber-900">
              {erroStatus}
            </p>
          )}
          <button
            type="submit"
            disabled={enviando}
            className="rounded-lg bg-emerald-700 px-4 py-2 text-sm font-medium text-white hover:bg-emerald-800 disabled:opacity-50"
          >
            {enviando ? "Salvando…" : "Atualizar status"}
          </button>
        </form>
      </section>

      <section className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
        <h2 className="text-lg font-medium">Histórico de status</h2>
        <p className="text-sm text-slate-500">
          Do mais antigo ao mais recente
        </p>
        <ol className="mt-4 space-y-3">
          {historico.map((item, index) => (
            <li
              key={`${item.data}-${item.status}-${index}`}
              className="flex gap-3 border-l-2 border-emerald-200 pl-4"
            >
              <div>
                <p className="font-medium text-emerald-900">{item.status}</p>
                <p className="text-xs text-slate-500">
                  {formatarData(item.data)}
                </p>
                <p className="mt-1 text-sm text-slate-700">
                  {item.observacao}
                </p>
              </div>
            </li>
          ))}
        </ol>
        {historico.length === 0 && (
          <p className="mt-4 text-sm text-slate-500">Sem registros ainda.</p>
        )}
      </section>
    </div>
  );
}

function sugerirProximoStatus(atual: StatusSolicitacao): StatusSolicitacao {
  if (atual === "ABERTA") {
    return "EM_ANDAMENTO";
  }
  if (atual === "EM_ANDAMENTO") {
    return "RESOLVIDA";
  }
  return "EM_ANDAMENTO";
}
